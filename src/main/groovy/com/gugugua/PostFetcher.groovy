package com.gugugua

import java.util.regex.Pattern


/**
 * @author cairne flashsword20@gmail.com
 * @date 2012-6-7
 */
class PostFetcher {

    Pattern list;

    Pattern post;

    String startPage;

    public void each(Closure closure){
        Set downloaded = [];
        List toDownload = [startPage];
        while (!toDownload.empty){
            def url = toDownload.pop();
            def html = url.toURL().text;
            if (url==~post){
                closure.call(url,html);
            }else {
                def matcher = html=~list;
                matcher.each{
                    if (!downloaded.contains(it)){
                        toDownload.push(it);
                        downloaded.add(it);
                    }
                }
                matcher = html=~post;
                matcher.each{
                    if (!downloaded.contains(it)){
                        toDownload.push(it);
                        downloaded.add(it);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        def fetcher = new PostFetcher();
        def str = 'http://blog\\.sina\\.com\\.cn/s/articlelist_[\\d_]+\\.html';
        fetcher.list = Pattern.compile(str);
        fetcher.post = ~/http:\/\/blog\.sina\.com\.cn\/s\/blog_\w+\.html/;
        fetcher.startPage="http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html";
        def count=0;
        fetcher.each {url,html->
            println count++;
            //new FileWriter(""+count++).write(html);
            //            println html;
        }
    }
}
