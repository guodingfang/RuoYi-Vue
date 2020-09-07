package com.jlkj.project.ws.domain;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @ClassName ImageCompress
 * @Description 图片压缩类
 * @Author liujie
 * @Date 2020/8/13 17:50
 **/
public class ImageCompress {
    private static final long serialVersionUID = 9185408039436223536L;
    private BufferedImage image;
    private Integer width;
    private Integer height;
    public ImageCompress() {
        super();
    }
    public ImageCompress(BufferedImage image) {
        super();
        this.image = image;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }
    public Image getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public Integer getWidth() {
        return width;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public Integer getHeight() {
        return height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    @Override
    public String toString() {
        return "ImageCompress [image=" + image + ", width=" + width + ", height=" + height + "]";
    }

    public BufferedImage resizeByWidth(int w){
        int h = (this.height/this.width)*w;

        return resizeFix(w,h);
    }

    public BufferedImage resizeByHeight(int h ){
        int w = (this.width/this.height)*h;

        return resizeFix(w,h);
    }

    public BufferedImage resizeFix(int w , int h){
        BufferedImage pressImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //创建Graphic,并在Graphic上画压缩后的图片
        Graphics graphic = pressImage.createGraphics();
        graphic.drawImage(this.image, 0, 0,w, h, null);
        return pressImage;
    }
}
