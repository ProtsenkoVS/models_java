package j6lambda;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.*;
import javax.imageio.ImageIO;

public class Mandelbrot {
	// image - буфер для створення образу множини Мальдерброта
	int width = 2*1920;
	int height = 2*1080;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	// кількість ітерацій
	int max = 1000;  
    // масив кольорів, що використовуються
    int black = 0;
    int[] colors = new int[max];
    
    public Mandelbrot(){ 
    	// заповнення масиву кольорів
    	for (int i = 0; i<max; i++) {
    		colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
    	}
    }
    
    // головна функція, що обчислює колір пікселя з координатами t  
	private int findColor(Coor t){
		int col = t.x; int row = t.y;		
        double c_re = (col - width/2)*4.0/width;
        double c_im = (row - height/2)*4.0/width;
        double x = 0, y = 0;
        int iteration = 0;
        while (x*x+y*y < 4 && iteration < max) {
            double x_new = x*x-y*y+c_re;
            y = 2*x*y+c_im;
            x = x_new;
            iteration++;
        } 
        return  (iteration<max)? colors[iteration]: black;
	}
	// описуємо координати одного пікселя
	private class Coor{
		int x, y;
		Coor(int xi, int yi){
			x=xi; y=yi;
		}
	}	
	// описуємо один піксель 
	private class Pixel{
		int x, y, color;
		Pixel(Coor t){
			x=t.x; y=t.y; color = findColor(t);
		}
		void addImage(){image.setRGB(x, y, color);}
	}
	
	// формуємо потік кординат усіх пікселів образу
	private Stream<Coor> allPixel(){
		return IntStream.range(0,width).boxed()
                        .flatMap(x-> IntStream.range(0, height).mapToObj(y-> new Coor(x,y)));
	}
	
	// формуємо файл з множеством Мандельброта nmFile послідовно
	public void sequantialForm(String nmFile) throws Exception {
		allPixel().sequential().map(Pixel::new).forEach(Pixel::addImage);
		ImageIO.write(image, "png", new File(nmFile)); 
	}
	
	// формуємо файл з множеством Мандельброта nmFile паралельно
	public void parallelForm(String nmFile) throws Exception {
		allPixel().parallel().map(Pixel::new).forEach(Pixel::addImage);
		ImageIO.write(image, "png", new File(nmFile)); 
	}	

	public static void main(String[] args) throws Exception  {
		Statistic st = new Statistic();
		Mandelbrot ms = new Mandelbrot();
		st.beginStage("ParallelStream");
		ms.parallelForm("mandelbrotPar.png");;
		st.beginStage("SequentialStream");
		ms.sequantialForm("mandelbrotTest.png");
		st.endStage();
		System.out.println(st.toString());	
		
		
	}	
	
}
