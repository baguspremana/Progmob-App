package com.example.user_pc.semnas_ti.bantuan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user_pc.semnas_ti.model.FaqUserResponse;
import com.example.user_pc.semnas_ti.model.InfoSeminarResponse;
import com.example.user_pc.semnas_ti.model.Profile;
import com.example.user_pc.semnas_ti.model.Ticket;
import com.example.user_pc.semnas_ti.model.TicketPayment;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="semnas.sql";
    private static final int DATABASE_VERSION=8;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_FAQ="CREATE TABLE "+FaqUserResponse.Entry.TABLE_NAME+" ( "+
                FaqUserResponse.Entry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                FaqUserResponse.Entry.COLUMN_ID+" INTEGER,"+
                FaqUserResponse.Entry.COLUMN_USER_ID+" INTEGER,"+
                FaqUserResponse.Entry.COLUMN_QUESTION+" TEXT,"+
                FaqUserResponse.Entry.COLUMN_ANSWER+" TEXT,"+
                FaqUserResponse.Entry.COLUMN_UPDATED+" TEXT );";

        String CREATE_TABLE_INFO="CREATE TABLE "+InfoSeminarResponse.Entry.TABLE_NAME+" ( "+
                InfoSeminarResponse.Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                InfoSeminarResponse.Entry.COLUMN_ID+" INTEGER,"+
                InfoSeminarResponse.Entry.COLUMN_NAME+" TEXT,"+
                InfoSeminarResponse.Entry.COLUMN_THEME+" TEXT,"+
                InfoSeminarResponse.Entry.COLUMN_DESC+" TEXT,"+
                InfoSeminarResponse.Entry.COLUMN_SCHEDULE+" TEXT,"+
                InfoSeminarResponse.Entry.COLUMN_LOC+" TEXT,"+
                InfoSeminarResponse.Entry.COLUMN_TICKET+" INTEGER );";

        String CREATE_TABLE_DETAIL_TICKET="CREATE TABLE "+Ticket.Entry.TABLE_NAME+" ( "+
                Ticket.Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Ticket.Entry.COLUMN_ID+" INTEGER,"+
                Ticket.Entry.COLUMN_BOOKING_ID+" INTEGER,"+
                Ticket.Entry.COLUMN_QR_CODE+" TEXT,"+
                Ticket.Entry.COLUMN_BOOKING_NAME+" TEXT,"+
                Ticket.Entry.COLUMN_BOOKING_EMAIL+" TEXT,"+
                Ticket.Entry.COLUMN_BOOKING_CONTACT+" TEXT,"+
                Ticket.Entry.COLUMN_BOOKING_VEGET+" INTEGER,"+
                Ticket.Entry.COLUMN_BOOKING_INSTITUTION+" TEXT,"+
                Ticket.Entry.COLUMN_BOOKING_PRICE+" INTEGER,"+
                Ticket.Entry.COLUMN_BOOKING_ADD+" TEXT,"+
                Ticket.Entry.COLUMN_STATUS+" INTEGER );";

        String CREATE_TABLE_MASTER_TICKET="CREATE TABLE "+TicketPayment.Entry.TABLE_NAME+" ( "+
                TicketPayment.Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                TicketPayment.Entry.COLUMN_ID+" INTEGER,"+
                TicketPayment.Entry.COLUMN_USER_ID+" INTEGER,"+
                TicketPayment.Entry.COLUMN_STATUS+" INTEGER,"+
                TicketPayment.Entry.COLUMN_PHOTO+" TEXT,"+
                TicketPayment.Entry.COLUMN_ETC+" TEXT,"+
                TicketPayment.Entry.COLUMN_JUMLAH+" INTEGER,"+
                TicketPayment.Entry.COLUMN_TOTAL_HARGA+" TEXT,"+
                TicketPayment.Entry.COLUMN_CREATED_AT+" TEXT,"+
                TicketPayment.Entry.COLUMN_UPDATED_AT+" TEXT );";


        String CREATE_TABLE_PROFILE="CREATE TABLE "+Profile.Entry.TABLE_NAME+" ( "+
                Profile.Entry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Profile.Entry.COLUMN_ID+" INTEGER,"+
                Profile.Entry.COLUMN_NAME+" TEXT,"+
                Profile.Entry.COLUMN_EMAIL+" TEXT,"+
                Profile.Entry.COLUMN_CONTACT+" TEXT,"+
                Profile.Entry.COLUMN_GENDER+" INTEGER,"+
                Profile.Entry.COLUMN_PHOTO+" TEXT );";

        sqLiteDatabase.execSQL(CREATE_TABLE_FAQ);
        sqLiteDatabase.execSQL(CREATE_TABLE_INFO);
        sqLiteDatabase.execSQL(CREATE_TABLE_DETAIL_TICKET);
        sqLiteDatabase.execSQL(CREATE_TABLE_MASTER_TICKET);
        sqLiteDatabase.execSQL(CREATE_TABLE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE_FAQ="DROP TABLE "+FaqUserResponse.Entry.TABLE_NAME+";";
        String DROP_TABLE_INFO="DROP TABLE "+InfoSeminarResponse.Entry.TABLE_NAME+";";
        String DROP_TABLE_DETAIL_TICKET="DROP TABLE "+Ticket.Entry.TABLE_NAME+";";
        String DROP_TABLE_MASTER_TICKET="DROP TABLE "+TicketPayment.Entry.TABLE_NAME+";";
        String DROP_TABLE_PROFILE="DROP TABLE "+Profile.Entry.TABLE_NAME+";";

        sqLiteDatabase.execSQL(DROP_TABLE_FAQ);
        sqLiteDatabase.execSQL(DROP_TABLE_INFO);
        sqLiteDatabase.execSQL(DROP_TABLE_DETAIL_TICKET);
        sqLiteDatabase.execSQL(DROP_TABLE_MASTER_TICKET);
        sqLiteDatabase.execSQL(DROP_TABLE_PROFILE);
        onCreate(sqLiteDatabase);
    }

    /*================================================================================================
                                             LOCAL FAQ USER
    ================================================================================================*/

    public long insertFAQ(int id, int userId, String question, String answer, String updated){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(FaqUserResponse.Entry.COLUMN_ID, id);
        contentValues.put(FaqUserResponse.Entry.COLUMN_USER_ID, userId);
        contentValues.put(FaqUserResponse.Entry.COLUMN_QUESTION, question);
        contentValues.put(FaqUserResponse.Entry.COLUMN_ANSWER, answer);
        contentValues.put(FaqUserResponse.Entry.COLUMN_UPDATED, updated);

        long lastID = sqLiteDatabase.insert(FaqUserResponse.Entry.TABLE_NAME, null, contentValues);

        return lastID;
    }

    public List<FaqUserResponse> selectFAQ(){
        List<FaqUserResponse> faqUserResponses = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        Cursor cursor=sqLiteDatabase.query(FaqUserResponse.Entry.TABLE_NAME, null, null, null, null,
                null, null);

//        int count=cursor.getCount();

        if (cursor != null){
            cursor.moveToFirst();
            do{
                int id=cursor.getInt(cursor.getColumnIndex(FaqUserResponse.Entry.COLUMN_ID));
                int userId=cursor.getInt(cursor.getColumnIndex(FaqUserResponse.Entry.COLUMN_USER_ID));
                String question=cursor.getString(cursor.getColumnIndex(FaqUserResponse.Entry.COLUMN_QUESTION));
                String answer=cursor.getString(cursor.getColumnIndex(FaqUserResponse.Entry.COLUMN_ANSWER));
                String updated=cursor.getString(cursor.getColumnIndex(FaqUserResponse.Entry.COLUMN_UPDATED));

                FaqUserResponse tmp=new FaqUserResponse(id, userId, question, answer, updated);

                faqUserResponses.add(tmp);
            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return faqUserResponses;
    }

    public void deleteFAQ(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(FaqUserResponse.Entry.TABLE_NAME, null, null);
    }

    /*================================================================================================
                                             LOCAL INFO SEMINAR USER
    ================================================================================================*/

    public long insertInfo(int id, String name, String theme, String desc, String schedule, String location, int tiket){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(InfoSeminarResponse.Entry.COLUMN_ID, id);
        contentValues.put(InfoSeminarResponse.Entry.COLUMN_NAME, name);
        contentValues.put(InfoSeminarResponse.Entry.COLUMN_THEME, theme);
        contentValues.put(InfoSeminarResponse.Entry.COLUMN_DESC, desc);
        contentValues.put(InfoSeminarResponse.Entry.COLUMN_SCHEDULE, schedule);
        contentValues.put(InfoSeminarResponse.Entry.COLUMN_LOC, location);
        contentValues.put(InfoSeminarResponse.Entry.COLUMN_TICKET, tiket);

        long lastID = sqLiteDatabase.insert(InfoSeminarResponse.Entry.TABLE_NAME, null, contentValues);

        return lastID;
    }

    public InfoSeminarResponse selectInfo(){
        InfoSeminarResponse infoSeminarResponse=null;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        Cursor cursor=sqLiteDatabase.query(InfoSeminarResponse.Entry.TABLE_NAME, null, null, null, null,
            null, null);

        if (cursor != null){
            cursor.moveToFirst();
            do{
                int id=cursor.getInt(cursor.getColumnIndex(InfoSeminarResponse.Entry.COLUMN_ID));
                String name=cursor.getString(cursor.getColumnIndex(InfoSeminarResponse.Entry.COLUMN_NAME));
                String theme=cursor.getString(cursor.getColumnIndex(InfoSeminarResponse.Entry.COLUMN_THEME));
                String desc=cursor.getString(cursor.getColumnIndex(InfoSeminarResponse.Entry.COLUMN_DESC));
                String schedule=cursor.getString(cursor.getColumnIndex(InfoSeminarResponse.Entry.COLUMN_SCHEDULE));
                String location=cursor.getString(cursor.getColumnIndex(InfoSeminarResponse.Entry.COLUMN_LOC));
                int tiket=cursor.getInt(cursor.getColumnIndex(InfoSeminarResponse.Entry.COLUMN_TICKET));
                infoSeminarResponse=new InfoSeminarResponse(id, name, theme, desc, schedule, location, tiket);

            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return infoSeminarResponse;
    }

    public void deleteInfo(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(InfoSeminarResponse.Entry.TABLE_NAME, null, null);
    }

    /*================================================================================================
                                             LOCAL DETAIL TICKET USER
    ================================================================================================*/

    public long insertTicketDetail(int id, String qrcode, String name, String email, String contact, int veget, String instansi, int price, String created, int status){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Ticket.Entry.COLUMN_ID, id);
        contentValues.put(Ticket.Entry.COLUMN_QR_CODE, qrcode);
        contentValues.put(Ticket.Entry.COLUMN_BOOKING_NAME, name);
        contentValues.put(Ticket.Entry.COLUMN_BOOKING_EMAIL, email);
        contentValues.put(Ticket.Entry.COLUMN_BOOKING_CONTACT, contact);
        contentValues.put(Ticket.Entry.COLUMN_BOOKING_VEGET, veget);
        contentValues.put(Ticket.Entry.COLUMN_BOOKING_INSTITUTION, instansi);
        contentValues.put(Ticket.Entry.COLUMN_BOOKING_PRICE, price);
        contentValues.put(Ticket.Entry.COLUMN_BOOKING_ADD, created);
        contentValues.put(Ticket.Entry.COLUMN_STATUS, status);

        long lastID=sqLiteDatabase.insert(Ticket.Entry.TABLE_NAME, null, contentValues);

        return lastID;
    }

    public List<Ticket> selectTicketDetail(){
        List<Ticket> ticketList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        Cursor cursor=sqLiteDatabase.query(Ticket.Entry.TABLE_NAME, null, null, null, null,
                null, null);

        if (cursor != null){
            cursor.moveToFirst();
            do{
                int id=cursor.getInt(cursor.getColumnIndex(Ticket.Entry.COLUMN_ID));
                String qrcode=cursor.getString(cursor.getColumnIndex(Ticket.Entry.COLUMN_QR_CODE));
                String name=cursor.getString(cursor.getColumnIndex(Ticket.Entry.COLUMN_BOOKING_NAME));
                String email=cursor.getString(cursor.getColumnIndex(Ticket.Entry.COLUMN_BOOKING_EMAIL));
                String contact=cursor.getString(cursor.getColumnIndex(Ticket.Entry.COLUMN_BOOKING_CONTACT));
                int veget=cursor.getInt(cursor.getColumnIndex(Ticket.Entry.COLUMN_BOOKING_VEGET));
                String instansi=cursor.getString(cursor.getColumnIndex(Ticket.Entry.COLUMN_BOOKING_INSTITUTION));
                int price=cursor.getInt(cursor.getColumnIndex(Ticket.Entry.COLUMN_BOOKING_PRICE));
                String created=cursor.getString(cursor.getColumnIndex(Ticket.Entry.COLUMN_BOOKING_ADD));
                int status=cursor.getInt(cursor.getColumnIndex(Ticket.Entry.COLUMN_STATUS));

                Ticket tmp=new Ticket(id, qrcode, name, email, contact, veget, instansi, price, created, status);

                ticketList.add(tmp);
            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return ticketList;
    }

    public void deleteTicketDetail(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(Ticket.Entry.TABLE_NAME, null, null);
    }

    /*================================================================================================
                                             LOCAL MASTER TICKET USER
    ================================================================================================*/

    public long insertTicket(int id, int user_id, int status, String photo, String etc, int jumlah, String total, String created, String updated){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(TicketPayment.Entry.COLUMN_ID, id);
        contentValues.put(TicketPayment.Entry.COLUMN_USER_ID, user_id);
        contentValues.put(TicketPayment.Entry.COLUMN_STATUS, status);
        contentValues.put(TicketPayment.Entry.COLUMN_PHOTO, photo);
        contentValues.put(TicketPayment.Entry.COLUMN_ETC, etc);
        contentValues.put(TicketPayment.Entry.COLUMN_JUMLAH, jumlah);
        contentValues.put(TicketPayment.Entry.COLUMN_TOTAL_HARGA, total);
        contentValues.put(TicketPayment.Entry.COLUMN_CREATED_AT, created);
        contentValues.put(TicketPayment.Entry.COLUMN_UPDATED_AT, updated);

        long lastID=sqLiteDatabase.insert(TicketPayment.Entry.TABLE_NAME, null, contentValues);

        return lastID;
    }

    public List<TicketPayment> selectTicket(){
        List<TicketPayment> ticketPayments = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        Cursor cursor=sqLiteDatabase.query(TicketPayment.Entry.TABLE_NAME, null, null, null, null,
                null, null);

//        int count=cursor.getCount();

        if (cursor != null){
            cursor.moveToFirst();
            do{
                int id=cursor.getInt(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_ID));
                int user_id=cursor.getInt(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_USER_ID));
                int status=cursor.getInt(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_STATUS));
                String photo=cursor.getString(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_PHOTO));
                String etc=cursor.getString(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_ETC));
                int jumlah=cursor.getInt(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_JUMLAH));
                String total=cursor.getString(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_TOTAL_HARGA));
                String created=cursor.getString(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_CREATED_AT));
                String updated=cursor.getString(cursor.getColumnIndex(TicketPayment.Entry.COLUMN_UPDATED_AT));

                TicketPayment tmp=new TicketPayment(id, user_id, status, photo, etc, jumlah, total, created, updated);

                ticketPayments.add(tmp);
            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return ticketPayments;
    }

    public void deleteTicket(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(TicketPayment.Entry.TABLE_NAME, null, null);
    }

    /*================================================================================================
                                             LOCAL PROFILE USER
    ================================================================================================*/

    public long insertProfile(int id, String name, String email, String contact, int gender, String photo){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Profile.Entry.COLUMN_ID, id);
        contentValues.put(Profile.Entry.COLUMN_NAME, name);
        contentValues.put(Profile.Entry.COLUMN_EMAIL, email);
        contentValues.put(Profile.Entry.COLUMN_CONTACT, contact);
        contentValues.put(Profile.Entry.COLUMN_GENDER, gender);
        contentValues.put(Profile.Entry.COLUMN_PHOTO, photo);

        long lastID=sqLiteDatabase.insert(Profile.Entry.TABLE_NAME, null, contentValues);

        return lastID;
    }

    public Profile selectProfile(){
        Profile profile=null;
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();

        Cursor cursor=sqLiteDatabase.query(Profile.Entry.TABLE_NAME, null, null, null, null,
                null, null);

        if (cursor != null){
            cursor.moveToFirst();
            do{
                int id=cursor.getInt(cursor.getColumnIndex(Profile.Entry.COLUMN_ID));
                String name=cursor.getString(cursor.getColumnIndex(Profile.Entry.COLUMN_NAME));
                String email=cursor.getString(cursor.getColumnIndex(Profile.Entry.COLUMN_EMAIL));
                String contact=cursor.getString(cursor.getColumnIndex(Profile.Entry.COLUMN_CONTACT));
                int gender=cursor.getInt(cursor.getColumnIndex(Profile.Entry.COLUMN_GENDER));
                String photo=cursor.getString(cursor.getColumnIndex(Profile.Entry.COLUMN_PHOTO));
                profile=new Profile(id, name, email, contact, gender, photo);

            }while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return profile;
    }

    public void deleteProfile(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete(Profile.Entry.TABLE_NAME, null, null);
    }
}
