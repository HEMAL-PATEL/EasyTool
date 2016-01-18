<img src="http://i.hizliresim.com/zrM9Mj.png" align="left" />
# EasyTool
> Contains basic tools of image works like loading image from url, making an image circle or blur.


###Simple Usage

First, you must modify your image view like this to use library's methods

    <com.xionces.EasyTool.ImageView
            android:layout_width="200dp"
            android:layout_height="200dp"
            android:id="@+id/imageView1"
    />

Now you can start using methods.There are four methods.

- L (Load from Url)
- LAB ( Load and Blur)
- LAC ( Load and Circle)
- LABAC (Load and Blur and Circle)

### L (Load from Url)

It's a very simple to use. You must just set an url of image which is what you want to load.

- Example
img.L("SomeUrlofYourImage");

### LAB(Load and Blur)

This method works for make your image blur. There are 3 parameters. 

img.LAB(String url,Context context, Float radius)

- Example
img.LAB("SomeUrlofYourImage",getApplicationContext,7.5f);


## LAC (Load and Circle)

This method load image from your url and make circle with your image's width and height.

- Example
img.LAC("SomeUrlofYourImage");


## LABAC(Load and Blur and Circle)

This method is a combination of the others. It's a very fast way if you want to make all of them.Usage is the same way of LAB.

-Example
img.LAB("SomeUrlofYourImage",getApplicationContext,7.5f);









