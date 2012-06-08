package com.gugugua



/**
 * @author cairne flashsword20@gmail.com
 * @date 2012-6-7
 */
class XmlConfig {

    Map posts = [:];

    Map lists = [:];

    Map titles = [:];

    Map contents=[:];

    Map times=[:];

    Map tags=[:];

    public void loadFromConfig(filename){
        def config = new XmlSlurper().parse(new File(filename));
        config.blog.each {
            lists[it.name.toString()]=it.list.toString();
            posts[it.name.toString()]=it.post.toString();
            titles[it.name.toString()]=it.title.toString();
            contents[it.name.toString()]=it.content.toString();
        }
    }

    public static void main(args){
        def config=new XmlConfig();
        config.loadFromConfig("config.xml");
        println config.lists;
        println config.lists.get("sina");
        def a = [aaa:1]
        println a['aaa']
        assert config.titles.size()>0;
        assert config.contents.size()>0;
        assert config.posts.size()>0;
        assert config.lists.size()>0;
    }
}
