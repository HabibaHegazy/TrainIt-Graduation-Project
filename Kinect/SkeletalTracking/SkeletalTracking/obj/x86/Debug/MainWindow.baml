   M S B A M L   `   `   `  ����^  ZMicrosoft.Samples.Kinect.WpfViewers, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null�^clr-namespace:Microsoft.Samples.Kinect.WpfViewers;assembly=Microsoft.Samples.Kinect.WpfViewers#Microsoft.Samples.Kinect.WpfViewers  5   	    SkeletalTracking!   SkeletalTracking.MainWindow   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   mmy^clr-namespace:Microsoft.Samples.Kinect.WpfViewers;assembly=Microsoft.Samples.Kinect.WpfViewers     5   	   -   5          � Title$  
MainWindow��5   	   $	��600��6   $��1512.303��6)    � WindowState$ 	Maximized=�5   "   .��5      �� -   5        $��
MainCanvas��5      .+�5   
   ;   5Microsoft.Samples.Kinect.WpfViewers.KinectColorViewer  -   5        $��kinectColorViewer1��5   W    �� Left$ 0��6   
 �� Top$ 0��6/   $	��640��6>   $	��480��6J   5   /Microsoft.Samples.Kinect.WpfViewers.ImageViewer   Kinect 5	      ��  �� ElementName$ kinectSensorChooser1�� �� Path$ Kinect�A� 5
   
   -   5        $��leftEllipse��5
   ?   $ 10��6   $ 50��6#   $��60��63   $��60��6S   ���	#FF4D298D J� Opacity$ 1Q�6o   
�������6{   A� 5   
   -   5        $��rightEllipse��5   P   $ 99��6   $ 50��6#   ���	#FF2CACE3$��60��6D   $��60��6d   $ 1Q�6o   
������� J� RenderTransformOrigin$ 0.636,1.147&�6�   =   7Microsoft.Samples.Kinect.WpfViewers.KinectSensorChooser  5   
   -   5        $��kinectSensorChooser1��5   D   $	 250��6!   $	 380��63   $	��328��6`   >   8Microsoft.Samples.Kinect.WpfViewers.KinectSkeletonViewer  5   
   -   5        $	��240��5   "   $	 713��6/   $ 39��6A   $	��320��6Q    6�   �� *Kinect+$ kinectSensorChooser1��A� 5   
   -   5        $��Brown��5   k   ���	#FF4D200D$��60��6#   $	 223��6/   
�������$ 50��6P   $��60��6`   6k   A� 5   
   -	   5        $��	LightGray��5   I   
�������$��60��6#   
�������$��60��6>   $ 72��6Z   $	 191��6k   A� 5   
   -
   5        $��
LightCoral��5   J   
�������$��60��6$   
�������$��60��6?   $	 439��6\   $	 141��6n   5      5      