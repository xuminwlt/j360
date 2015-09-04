package me.j360.web.img;

/**
 * Created with us2 -> com.fz.us.web.action.
 * User: min_xu
 * Date: 2015/6/30
 * Time: 11:33
 * 说明：
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class MyImgFilter {
    BufferedImage image;
    private int iw, ih;
    private int[] pixels;
    public MyImgFilter(BufferedImage image) {
        this.image = image;
        iw = image.getWidth();
        ih = image.getHeight();
        pixels = new int[iw * ih];
    }
    public BufferedImage changeGrey() {
        PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih, pixels,0, iw);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
// 设定二值化的域值，默认值为100
        int grey = 100;
// 对图像进行二值化处理，Alpha值保持不变
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 0; i < iw * ih; i++) {
            int red, green, blue;
            int alpha = cm.getAlpha(pixels[i]);
            if (cm.getRed(pixels[i]) > grey) {
                red = 255;
            } else {
                red = 0;
            }
            if (cm.getGreen(pixels[i]) > grey) {
                green = 255;
            } else {
                green = 0;
            }
            if (cm.getBlue(pixels[i]) > grey) {
                blue = 255;
            } else {
                blue = 0;
            }
            pixels[i] = alpha << 24 | red << 16 | green << 8 | blue; //通过移位重新构成某一点像素的RGB值
        }
// 将数组中的象素产生一个图像
        Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw));
        image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR );
        image.createGraphics().drawImage(tempImg, 0, 0, null);
        return image;
    }
    public BufferedImage getMedian() {
        PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, iw, ih,
                pixels,
                0, iw);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
// 对图像进行中值滤波，Alpha值保持不变
        ColorModel cm = ColorModel.getRGBdefault();
        for (int i = 1; i < ih - 1; i++) {
            for (int j = 1; j < iw - 1; j++) {
                int red, green, blue;
                int alpha = cm.getAlpha(pixels[i * iw + j]);
// int red2 = cm.getRed(pixels[(i - 1) * iw + j]);
                int red4 = cm.getRed(pixels[i * iw + j - 1]);
                int red5 = cm.getRed(pixels[i * iw + j]);
                int red6 = cm.getRed(pixels[i * iw + j + 1]);
// int red8 = cm.getRed(pixels[(i + 1) * iw + j]);
// 水平方向进行中值滤波
                if (red4 >= red5) {
                    if (red5 >= red6) {
                        red = red5;
                    } else {
                        if (red4 >= red6) {
                            red = red6;
                        } else {
                            red = red4;
                        }
                    }
                } else {
                    if (red4 > red6) {
                        red = red4;
                    } else {
                        if (red5 > red6) {
                            red = red6;
                        } else {
                            red = red5;
                        }
                    }
                }
                int green4 = cm.getGreen(pixels[i * iw + j - 1]);
                int green5 = cm.getGreen(pixels[i * iw + j]);
                int green6 = cm.getGreen(pixels[i * iw + j + 1]);
// 水平方向进行中值滤波
                if (green4 >= green5) {
                    if (green5 >= green6) {
                        green = green5;
                    } else {
                        if (green4 >= green6) {
                            green = green6;
                        } else {
                            green = green4;
                        }
                    }
                } else {
                    if (green4 > green6) {
                        green = green4;
                    } else {
                        if (green5 > green6) {
                            green = green6;
                        } else {
                            green = green5;
                        }
                    }
                }
// int blue2 = cm.getBlue(pixels[(i - 1) * iw + j]);
                int blue4 = cm.getBlue(pixels[i * iw + j - 1]);
                int blue5 = cm.getBlue(pixels[i * iw + j]);
                int blue6 = cm.getBlue(pixels[i * iw + j + 1]);
// int blue8 = cm.getBlue(pixels[(i + 1) * iw + j]);
// 水平方向进行中值滤波
                if (blue4 >= blue5) {
                    if (blue5 >= blue6) {
                        blue = blue5;
                    } else {
                        if (blue4 >= blue6) {
                            blue = blue6;
                        } else {
                            blue = blue4;
                        }
                    }
                } else {
                    if (blue4 > blue6) {
                        blue = blue4;
                    } else {
                        if (blue5 > blue6) {
                            blue = blue6;
                        } else {
                            blue = blue5;
                        }
                    }
                }
                pixels[i * iw +
                        j] = alpha << 24 | red << 16 | green << 8 | blue;
            }
        }
// 将数组中的象素产生一个图像
        Image tempImg=Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(iw,ih, pixels, 0, iw));
        image = new BufferedImage(tempImg.getWidth(null),tempImg.getHeight(null), BufferedImage.TYPE_INT_BGR );
        image.createGraphics().drawImage(tempImg, 0, 0, null);
        return image;
    }
    public BufferedImage getGrey() {
        ColorConvertOp ccp=new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return image=ccp.filter(image,null);
    }
    //Brighten using a linear formula that increases all color values
    public BufferedImage getBrighten() {
        RescaleOp rop=new RescaleOp(1.25f, 0, null);
        return image=rop.filter(image,null);
    }
    //Blur by "convolving" the image with a matrix
    public BufferedImage getBlur() {
        float[] data = {
                .1111f, .1111f, .1111f,
                .1111f, .1111f, .1111f,
                .1111f, .1111f, .1111f, };
        ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data));
        return image=cop.filter(image,null);
    }
    // Sharpen by using a different matrix
    public BufferedImage getSharpen() {
        float[] data = {
                0.0f, -0.75f, 0.0f,
                -0.75f, 4.0f, -0.75f,
                0.0f, -0.75f, 0.0f};
        ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data));
        return image=cop.filter(image,null);
    }
    // 11) Rotate the image 180 degrees about its center point
    public BufferedImage getRotate() {
        AffineTransformOp atop=new AffineTransformOp(AffineTransform.getRotateInstance(Math.PI,image.getWidth()/2,image.getHeight()/2),
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return image=atop.filter(image,null);
    }
    public BufferedImage getProcessedImg()
    {
        return image;
    }

    //最近一直在开发一个用于自动发帖的工具，用HttpClient模拟客户端浏览器注册发帖。
    // 但是碰到了图形验证码的问题了，对单数字的验证码，通过一些OCR引擎，如：tesseract，AspriseOCR很容易解决问题。
    // 但碰到如CSDN论坛这中图形验证码就比较麻烦，必须先通过预处理。使图象二值化，黑白灰度，增加亮度。我的代码如下：
    public static void main(String[] args) throws IOException {
        String name = "d:\\test1.jpg";
        FileInputStream fin=new FileInputStream(name);
        BufferedImage bi=ImageIO.read(fin);
        MyImgFilter flt=new MyImgFilter(bi);
        flt.changeGrey();
        flt.getGrey();
        flt.getBrighten();
        bi=flt.getProcessedImg();
        //String pname=args[0].substring(0,args[0].lastIndexOf("."));
        String pname = "d:\\test2";
        File file = new File(pname+".jpg");
        ImageIO.write(bi, "jpg", file);
    }
}
