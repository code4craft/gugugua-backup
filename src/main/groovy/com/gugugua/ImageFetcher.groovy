package com.gugugua



/**
 * @author cairne flashsword20@gmail.com
 * @date 2012-6-8
 */
class ImageFetcher {

    def downloadMap = [:];

    public ImageFetcher(){
        new File(Context.path+"/images").mkdirs();
    }

    public String fetchImage(String html){
        def matcher = html=~/<img[^<>]+src=['"]([^'"]+)['"][^<>]+>/;
        return new RegexReplacer(matcher).replace{group-> return "<img src='${download(group)}'>"; };
    }

    def download(address) {
        def filename= downloadMap[address];
        if (!filename){
            filename="images/"+address.tokenize("/")[-1];
            Context.consle("downloading image "+address);
            downloadMap[address]=filename;
            def file = new FileOutputStream(Context.path+"/"+filename)
            def out = new BufferedOutputStream(file)
            out << new URL(address).openStream()
            out.close()
        }
        return filename;
    }

    public static void main(args){
        def fetcher = new ImageFetcher();
        println fetcher.fetchImage("<img src='asdasd/asdasd/asdasda.gs'>");
    }
}
