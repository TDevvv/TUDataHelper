# TUDataHelper
data helper and visual graphic printer

Writed with TUFileReader_.
# begin:

use : call TUGVisualizer#startDataSync create config.tdata

vars: -> config.tdata (it is a window config file you can set width,height,title,graphic point,other file paths)
title:
width:
height:
point_x:
point_y:
dw_column_file_name:
up_column_file_name:
data_points_file_name:

vars: -> dw_column_file_name-custom-.tdata (it is a column config file -down column- you can set name,length,rate_of_inc)
name:
length:
rate_of_inc:

up_column file same with dw_column,just you know its up.

vars: -> data_points_file_name-custom-.tdata (it is a data point file to setting points you can set names: and points)
example:

first_point: {x=10,y=100}
second_point: {x=20,y=200}
.......


names: {first_point,second_point,.......}

#end:
