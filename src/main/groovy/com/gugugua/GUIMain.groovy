package com.gugugua

import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.util.regex.Pattern




new SwingBuilder().edt {
    frame(title:'Gugugua Blog Backup', size:[600, 400], show: true) {
        Context.path = "blog-"+new Date().format("yyyy-MM-dd-hhmmss");
        new File(Context.path).mkdir();
        def xmlConfig = new XmlConfig();
        xmlConfig.loadFromConfig("config.xml");
        def imgFetcher = new ImageFetcher();
        borderLayout()
        textField= textField(text:"input the blog url here",id:  " expr " , constraints: BorderLayout.NORTH)
        scrollPane(constraints: BorderLayout.CENTER){
            textarea = textArea(editable:false ,text:"Ready");
        }
        Context.consle={ text->
            textarea.text+="\n"+text;
        }
        List items = []+xmlConfig.lists.keySet();
        def comboBox = comboBox(items:items,constraints: BorderLayout.EAST)
        button(text:'Start',
                actionPerformed: {
                    Thread.start{
                        try{
                            def type= comboBox.selectedItem;
                            def fetcher = new PostFetcher();
                            fetcher.list=Pattern.compile(xmlConfig.lists[type]);
                            fetcher.post=Pattern.compile(xmlConfig.posts[type]);
                            fetcher.startPage=textField.text;
                            textarea.text ="downloading...";
                            fetcher.each{ url,html->
                                Context.consle(url+"\tdownloaded.");
                                def htmlNew = imgFetcher.fetchImage(html);
                                def filename=Context.path+"/"+url.tokenize("/")[-1];
                                new FileWriter(filename).write(htmlNew);
                            }
                            Context.consle("done!");
                        }catch (Throwable e){
                            Context.consle(e);
                        }
                    }
                },
                constraints:BorderLayout.SOUTH)
    }
}