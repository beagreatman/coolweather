package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "cool_weather";
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;

	/**
	 * 构造方法私有化
	 * @param context
	 */
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	/**
	 * 获取CoolWeatherDB的实例
	 * @param context
	 * @return
	 */
	public synchronized static CoolWeatherDB getInstance(Context context) {
		if (coolWeatherDB==null) {
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * 将province的实例保存到数据库中
	 * @param province
	 * @return
	 */
	public long saveProvince(Province province) {
		if (province!=null) {
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			return db.insert("Province", null, values);
		}
		return -1;
	}
	
	/**
	 * 从数据库中读取全国所有的省份信息
	 * @return
	 */
	public List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);		
		if (cursor.moveToFirst()) {
			do {
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		if (cursor!=null) {
			cursor.close();
		}
		return list;
	}
	

	/**
	 * 将City的实例保存到数据库中
	 * @param City
	 * @return
	 */
	public long saveCity(City city) {
		if (city!=null) {
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			return db.insert("City", null, values);
		}
		return -1;
	}
	/**
	 * 从数据库中读取某省份下的所有城市信息
	 * @return
	 */
	public List<City> loadCities(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id = ?", 
				new String[]{String.valueOf(provinceId)}, null, null, null);		
		if (cursor.moveToFirst()) {
			do {
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		if (cursor!=null) {
			cursor.close();
		}
		return list;
	}
	/**
	 * 将City的实例保存到数据库中
	 * @param City
	 * @return
	 */
	public long saveCounty(County county) {
		if (county!=null) {
			ContentValues values=new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			return db.insert("County", null, values);
		}
		return -1;
	}
	/**
	 * 从数据库中读取某省份下的所有城市信息
	 * @return
	 */
	public List<County> loadCounties(int cityId){
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("County", null, "city_id = ?", 
				new String[]{String.valueOf(cityId)}, null, null, null);		
		if (cursor.moveToFirst()) {
			do {
				County county=new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			} while (cursor.moveToNext());
		}
		if (cursor!=null) {
			cursor.close();
		}
		return list;
	}

}
