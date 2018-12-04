package com.example.user_pc.semnas_ti.model;

import com.google.gson.annotations.SerializedName;

public class DashboardAdminResponse{

	@SerializedName("belum_verif")
	private int belumVerif;

	@SerializedName("hadir")
	private int hadir;

	@SerializedName("jumlah_ticket")
	private int jumlahTicket;

	@SerializedName("total_penjualan")
	private String totalPenjualan;

	@SerializedName("veget")
	private int veget;

	@SerializedName("verif")
	private int verif;

	public void setBelumVerif(int belumVerif){
		this.belumVerif = belumVerif;
	}

	public int getBelumVerif(){
		return belumVerif;
	}

	public void setHadir(int hadir){
		this.hadir = hadir;
	}

	public int getHadir(){
		return hadir;
	}

	public void setJumlahTicket(int jumlahTicket){
		this.jumlahTicket = jumlahTicket;
	}

	public int getJumlahTicket(){
		return jumlahTicket;
	}

	public void setTotalPenjualan(String totalPenjualan){
		this.totalPenjualan = totalPenjualan;
	}

	public String getTotalPenjualan(){
		return totalPenjualan;
	}

	public void setVeget(int veget){
		this.veget = veget;
	}

	public int getVeget(){
		return veget;
	}

	public void setVerif(int verif){
		this.verif = verif;
	}

	public int getVerif(){
		return verif;
	}

	@Override
 	public String toString(){
		return 
			"DashboardAdminResponse{" + 
			"belum_verif = '" + belumVerif + '\'' + 
			",hadir = '" + hadir + '\'' + 
			",jumlah_ticket = '" + jumlahTicket + '\'' + 
			",total_penjualan = '" + totalPenjualan + '\'' + 
			",veget = '" + veget + '\'' + 
			",verif = '" + verif + '\'' + 
			"}";
		}
}