package com.thundergod.data_helper.data_system;

import com.thundergod.data_helper.file_system.TUFileHandler;
import com.thundergod.data_helper.file_system.TUFileReader;
import com.thundergod.data_helper.file_system.TUFileWriter;
import com.thundergod.data_helper.file_system.annotations.dtype.DType;
import com.thundergod.data_helper.file_system.annotations.id.ID;
import com.thundergod.data_helper.file_system.annotations.reader_settings.IOSettings;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class TUGVisualizer extends JFrame {
    static DataGraphic graphic;
    static Graphics graphics;


    public void setDataGraphic(DataGraphic graphic){
        this.graphic = graphic;
    }
    public TUGVisualizer(String TITLE, DataGraphic graphic) {
        int k = 0;
        setDataGraphic(graphic);
        setTitle(TITLE);
        setVisible(true);
        setSize(1920, 1080);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        graphics = g;
        //drawGraphic(g,new GraphicColumn(new Rect(10,400),new Point(40,50)));
        if (graphic == null) {


        }else{
            graphic.drawDataGraphic(g);
        }

    }
    public static void refresh(){

    }
    public void drawGraphic(Graphics g,GraphicColumn first_graphic_column){
        g.drawRect(first_graphic_column.point.x,first_graphic_column.point.y,first_graphic_column.rect.width,first_graphic_column.rect.height);
        g.drawRect(first_graphic_column.point.x,first_graphic_column.point.y+first_graphic_column.rect.height,first_graphic_column.rect.height,first_graphic_column.rect.width);
    }
    public static class Point{
        int get_value_Dw;
        int get_value_Up;

        public void setGet_value_Up(int get_value_Up) {
            this.get_value_Up = get_value_Up;
        }

        public void setGet_valueDW(int get_value) {
            this.get_value_Dw = get_value;
        }

        int x;
        int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
    public static class Rect{
        int width;
        int height;

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public Rect(int width, int height){
            setHeight(height);
            setWidth(width);
        }

        @Override
        public String toString() {
            return "Rect{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }
    }
    public static class GraphicColumn{
        Rect rect;
        Point point;

        public Point getPoint() {
            return point;
        }

        public Rect getRect() {
            return rect;
        }

        public GraphicColumn(Rect rect, Point point){
            this.rect = rect;
            this.point = point;
        }

        @Override
        public String toString() {
            return "GraphicColumn{" +
                    "rect=" + rect +
                    ", point=" + point +
                    '}';
        }
    }
    public static class DataGraphic{
        public static int DW_HASH_INT = 0;
        public static int UP_HASH_INT = 1;
        HashMap<Integer,Point> dw_point_hash = new HashMap<>();
        HashMap<Integer,Point> up_point_hash = new HashMap<>();

        List<Point> oval_points = new ArrayList<>();

        public List<Point> getOval_points() {
            return oval_points;
        }

        public HashMap<Integer, Point> getDw_point_hash() {
            return dw_point_hash;
        }

        public HashMap<Integer, Point> getUp_point_hash() {
            return up_point_hash;
        }

        public Point getExactPoint(Point x,Point y){
            return  new Point(x.x,y.y);
        }

        public Point getPoint(int value, Column column){
            if (column  == getUpColumn()){
                return up_point_hash.get(value);
            }else if(column == getDwColumn()){
                return dw_point_hash.get(value);
            }else {
                return new Point(0,0);
            }
        }
        HashMap<Integer,Point> data_var_hash = new HashMap<>();
        public void addGraphicPoint(int key,Point point){
            data_var_hash.put(key,point);
        }

        public HashMap<Integer, Point> getData_var_hash() {
            return data_var_hash;
        }

        public void handleGraphicPoint(Graphics g){
            for (Point point:
                    data_var_hash.values()) {
                g.drawOval(point.x, point.y,10,10);
            }
        }

        Column dw_col;
        Column up_col;
        GraphicColumn column;
        public DataGraphic(GraphicColumn column){
            this.column = column;
            up_col = new Column(column.point.x,column.point.y,column.rect.width,column.rect.height);
            dw_col = new Column(column.point.x,column.point.y+column.rect.height,column.rect.height,column.rect.width);
        }
        public void drawDataGraphic(Graphics g){
            drawGraphic(g,column);
            handleIntVars(g);
            handleGraphicPoint(g);
            for (Point point:
                    oval_points) {
                g.drawOval(point.x,point.y,10,10);
            }
            drawLinesOnPoints(g);
        }
        public void handleIntVars(Graphics g){


            List<Integer> dw_int_list =   getDwColumn().field.getLastList();
            int i = 0;
            for (Integer integer:
                    dw_int_list) {
                g.drawString(integer+"",getDwColumn().x+i, getDwColumn().y+20);
                dw_point_hash.put(integer,new Point(getDwColumn().x+i, getDwColumn().y+20));

                if (Math.max(final_dw_rect_width, getDwColumn().field.rate_of_inc) == getDwColumn().field.rate_of_inc){
                    i+= getDwColumn().field.last/getDwColumn().field.rate_of_inc*100;
                }else{
                    i+= getDwColumn().width/getDwColumn().field.rate_of_inc*2;
                }




            }
            List<Integer> up_int_list = getUpColumn().field.getLastList();
            int j = 0;
            for (Integer integer_2:
                    up_int_list) {
                g.drawString(integer_2+"",getUpColumn().x-30, getDwColumn().y-j);
                up_point_hash.put(integer_2,new Point(getUpColumn().x-30, getDwColumn().y-j ));
                j+= getUpColumn().height/getUpColumn().field.rate_of_inc*5;
            }
            // System.out.println("up hash : "+up_point_hash.toString());
            // System.out.println("dw hash : "+dw_point_hash.toString());
        }
        public List<HashMap<Integer,Point>> handlePoints(){
            List<Integer> dw_int_list =   getDwColumn().field.getLastList();
            int i = 0;
            for (Integer integer:
                    dw_int_list) {
                dw_point_hash.put(integer,new Point(getDwColumn().x+i, getDwColumn().y+20));
                i+= getDwColumn().width/getDwColumn().field.rate_of_inc*2;
            }
            List<Integer> up_int_list = getUpColumn().field.getLastList();
            int j = 0;
            for (Integer integer_2:
                    up_int_list) {
                up_point_hash.put(integer_2,new Point(getUpColumn().x-30, getDwColumn().y-j ));
                j+=getUpColumn().field.rate_of_inc/1.1;
            }
            List<HashMap<Integer,Point>> list = new ArrayList<>();
            list.add(dw_point_hash);
            list.add(up_point_hash);
            return list;
        }
        int dw_rect_height;
        int up_rect_height;
        int dw_rect_width ;
        int up_rect_width;

        public void setDw_rect_height(int dw_rect_height) {
            this.dw_rect_height = dw_rect_height;
        }
        public void setDw_rect_width(int dw_rect_width) {
            this.dw_rect_width = dw_rect_width;
        }

        int final_dw_rect_width;
        int final_dw_rect_height;


        public void drawGraphic(Graphics g, GraphicColumn column){
            int dw_rect_height = column.rect.width;
            int up_rect_height = column.rect.height;
            int dw_rect_width = column.rect.height;
            int up_rect_width = column.rect.width;
            int legex = dw_rect_width/getDwColumn().field.rate_of_inc*100;
            g.drawRect(column.point.x,column.point.y,up_rect_width,up_rect_height);
            g.drawRect(column.point.x,column.point.y+column.rect.height,dw_rect_width+legex*dw_rect_height,dw_rect_height);
            init(g);
             final_dw_rect_width = dw_rect_width+legex*dw_rect_height;
             final_dw_rect_height = dw_rect_height;
        }
        public Column getUpColumn(){
            return up_col;
        }
        public Column getDwColumn(){
            return dw_col;
        }
        public void addDataField(DataField field,Column column){
            if (column==getDwColumn()){
                getDwColumn().setField(field);
            }else if (column==getUpColumn()){
                getUpColumn().setField(field);
            }else {
                return;
            }
        }
        public void init(Graphics g){
            handle_data(g,getUpColumn(),null);
            handle_data(g,getDwColumn(), ColType.DOWN);
        }
        public void handle_data(Graphics g,Column column,ColType NULLABLE_TYPE){
            if (column == null){
                return;
            }
            if (column.field == null){
                return;
            }
            if (NULLABLE_TYPE== ColType.DOWN){
                g.drawString(column.field.NAME, final_dw_rect_width, column.y);
            }else{
                g.drawString(column.field.NAME,column.x,column.y);
            }
        }

        public Point translate(int dw_value,int up_value){
            Point rtr_point= this.getExactPoint(this.handlePoints().get(DW_HASH_INT).get(dw_value),this.handlePoints().get(UP_HASH_INT).get(up_value));
            rtr_point.setGet_valueDW(dw_value);
            rtr_point.setGet_value_Up(up_value);
            return rtr_point;

        }
        public void addAPoint(int dw_val,int up_val){
            oval_points.add(translate(dw_val,up_val));
        }
        public void addAPoint(Point point){
            oval_points.add(translate(point.x,point.y));
        }

        public void drawLinesOnPoints(Graphics g){
            Point next_point = null;
            Point current_point = null;
            for (int i = 0; i<oval_points.size(); i++)
            {
                if (checkHasNext(oval_points,i+1)){
                    next_point = oval_points.get(i+1);
                }
                current_point = oval_points.get(i);


                g.drawLine(current_point.x,current_point.y,next_point.x,next_point.y);

                g.drawString(getDwColumn().field.NAME+" =  "+next_point.get_value_Dw,next_point.x+10, next_point.y-100);
                g.drawString(getUpColumn().field.NAME+" =  "+next_point.get_value_Up,next_point.x+10, next_point.y-75);

                int up_cost = Math.max(current_point.get_value_Up,next_point.get_value_Up)-Math.min(current_point.get_value_Up,next_point.get_value_Dw);
                g.drawString("gap on "+getUpColumn().field.NAME+":"+up_cost,next_point.x+10, next_point.y-50);
                int dw_cost = Math.max(current_point.get_value_Dw,next_point.get_value_Dw)-Math.min(current_point.get_value_Dw,next_point.get_value_Dw);
                g.drawString("gap on "+getDwColumn().field.NAME+": "+dw_cost,next_point.x+10, next_point.y);
            }
        }
        public boolean checkHasNext(List<?> list,int i){
            try{
                list.get(i);
                return true;
            }catch (IndexOutOfBoundsException e){
                return false;
            }
        }

        public void init_(Graphics g){
            int count = getDwColumn().field.last;
            for (int i = 0; i < count; i++) {
                g.drawString(i+"",i*20,300);
            }

        }

    }
    public enum ColType{
        DOWN,
        UP;
    }
    public  static class Column{
        int x, y, width, height;
        DataField field;

        public void setField(DataField field) {
            this.field = field;
        }

        public Column(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

    }
    public static class DataField{
        String NAME;
        int last;
        int rate_of_inc;
        public DataField(String NAME,int last,int rate_of_inc){
            this.NAME = NAME;
            this.last = last;
            this.rate_of_inc = rate_of_inc;
        }
        public List<Integer> getLastList(){
            List<Integer> return_last = new ArrayList<>();
            int add = 0;
            for (int i = 0; i < last/rate_of_inc+1; i++) {
                return_last.add(add);
                add +=rate_of_inc;
            }
            return return_last;
        }
        public void append(DataField field){
        }
    }
    @ID(ID = 1312)
    @IOSettings(MODE = TUFileReader.ReaderMode.DETAILED,SYNTAX_MODE = TUFileReader.ReaderMode.DEFAULT)
    @DType(file_extension = ".tdata",show_result = true)
    public static  TUFileReader READER = TUFileReader.create(TUFileHandler.createDataFile("test",TUGVisualizer.class,"READER"), TUGVisualizer.class);
    public static void resetInstance(){
        READER = TUFileReader.create(READER.getFILE(),TUGVisualizer.class);
    }
    public static void startDataSync(){

        resetReader("config");
        READER.setup(true);
        String title = READER.readWithValuePatterPlain("title:");
        resetScanner(READER);
        int height = Integer.parseInt(READER.readWithValuePatterPlain("height:"));
        resetScanner(READER);
        int width = Integer.parseInt(READER.readWithValuePatterPlain("width:"));
        resetScanner(READER);
        int point_x = Integer.parseInt(READER.readWithValuePatterPlain("point_x:"));
        resetScanner(READER);
        int point_y = Integer.parseInt(READER.readWithValuePatterPlain("point_y:"));
        resetScanner(READER);
        String dw_column_file_name = READER.readWithValuePatterPlain("dw_column_file_name:");
        resetScanner(READER);
        String up_column_file_name = READER.readWithValuePatterPlain("up_column_file_name:");
        resetScanner(READER);
        String data_points_file_name = READER.readWithValuePatterPlain("data_points_file_name:");
        resetScanner(READER);

        resetReader(dw_column_file_name);
        READER.setup(true);
        String dw_name = READER.readWithValuePatterPlain("name:");
        resetScanner(READER);
        int dw_length = Integer.parseInt(READER.readWithValuePatterPlain("length:"));
        resetScanner(READER);
        int dw_rate_of_inc = Integer.parseInt(READER.readWithValuePatterPlain("rate_of_inc:"));
        resetScanner(READER);


        resetReader(up_column_file_name);
        READER.setup(true);
        String up_name = READER.readWithValuePatterPlain("name:");
        resetScanner(READER);
        int up_length = Integer.parseInt(READER.readWithValuePatterPlain("length:"));
        resetScanner(READER);
        int up_rate_of_inc = Integer.parseInt(READER.readWithValuePatterPlain("rate_of_inc:"));
        resetScanner(READER);

        resetReader(data_points_file_name);
        READER.setup(true);
        String names_pl = READER.readWithValuePatterPlain("names:");
        resetScanner(READER);
        List<String> LIST = Arrays.asList(names_pl.split(","));
        List<String > non_bracket_list = getNonBracket(LIST);
        HashMap<String,List<Value>> HASH = new HashMap<>();

        for (String s:
             non_bracket_list) {
            resetScanner(READER);
            List<Value> VALUE_LIST = new ArrayList<>();
            String patterned = READER.readWithValuePatterPlain(s+":");
            List<String> list_values = Arrays.asList(patterned.split(","));
            List<String> non_bracket_patterned_list = getNonBracket(list_values);
            for (String fr:
                 non_bracket_patterned_list) {
                String name = fr.substring(0,fr.indexOf("=")+1);
                int value = Integer.parseInt(fr.substring(name.length()));
                name = name.substring(0,name.length()-1);
                Value value1 = new Value(name,value);
                VALUE_LIST.add(value1);

            }
            HASH.put(s,VALUE_LIST);
        }

        Rect ins_rect = new Rect(width,height);
        Point ins_point = new Point(point_x,point_y);
        GraphicColumn ins_graphic_column = new GraphicColumn(ins_rect,ins_point);
        DataGraphic ins_data_graphic = new DataGraphic(ins_graphic_column);
        DataField ins_dw_data_field  =new DataField(dw_name,dw_length,dw_rate_of_inc);
        DataField ins_up_data_field = new DataField(up_name,up_length,up_rate_of_inc);

        ins_data_graphic.addDataField(ins_dw_data_field,ins_data_graphic.getDwColumn());
        ins_data_graphic.addDataField(ins_up_data_field,ins_data_graphic.getUpColumn());

        for (List<Value> values:
             HASH.values()) {
            Point point_rtr = null;
            int x = 0;
            int y  = 0;
            for (Value value:
                 values) {
                if (value.NAME.equals("x")){
                    x = (int)value.Value;
                } else if (value.NAME.equals("y")) {
                    y = (int)value.Value;
                }else{
                    break;
                }
            }
            point_rtr = new Point(x,y);
            System.out.println(point_rtr.toString());
            ins_data_graphic.addAPoint(point_rtr.getX(),point_rtr.y);
        }

        TUGVisualizer ins_visualizer = new TUGVisualizer(title,ins_data_graphic);

    }
    public static class Value{
        String NAME;
        Object Value;

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public void setValue(Object value) {
            Value = value;
        }

        public String getNAME() {
            return NAME;
        }

        public Object getValue() {
            return Value;
        }

        public Value(String NAME, Object value) {
            this.NAME = NAME;
            Value = value;
        }

        @Override
        public String toString() {
            return "Value{" +
                    "NAME='" + NAME + '\'' +
                    ", Value=" + Value +
                    '}';
        }

    }
    public static List<String> getNonBracket(List<String> LIST){
        int i = 0;
        List<String> RTR_LIST = new ArrayList<>();
        for (String s:
                LIST) {
            if (s.length()==0){
                break;
            }
            if (i==0){
                s = s.substring(1);
            }
            if (i==LIST.size()-1){
                s = s.substring(0,s.length()-1);
            }
            RTR_LIST.add(s);
            i++;
        }
        return RTR_LIST;

    }
    public static void stabilization(String plain,String splitter){

        System.out.println(Arrays.toString(plain.split(splitter)));
    }

    public static void resetReader(String PATH){
        READER = TUFileReader.create(TUFileHandler.createDataFile(PATH, TUGVisualizer.class), TUGVisualizer.class);
    }
    public static void resetScanner(TUFileReader reader){
        try {
            reader.setSCANNER(new Scanner(reader.getFILE()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
