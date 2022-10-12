package com.digitusforum.chat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "FactDados")
public class FactDadosEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String factDadosId;
	private String idMoeda;
	private String memberId;
	private String empresa;
	private String dtCad;
	private String dtMov;
	private String comoConheceu;
	private String faixaIdade;
	private String tipoPessoa;
	private String uf;
	private String nivel;
	private String equipamento;
	private String vlrDepositoBrl;
	private String vlrSaqueBrl;
	private String vlrTradeBrl;
	private String vlrDepositoFeeBrl;
	private String vlrSaqueFeeBrl;
	private String vlrTradeFeeBrl;
	public FactDadosEntity() {
	}
	
	

	public String getFactDadosId() {
		return factDadosId;
	}



	public void setFactDadosId(String factDadosId) {
		this.factDadosId = factDadosId;
	}



	public String getIdMoeda() {
		return idMoeda;
	}
	public void setIdMoeda(String idMoeda) {
		this.idMoeda = idMoeda;
	}
	
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getDtCad() {
		return dtCad;
	}
	public void setDtCad(String dtCad) {
		this.dtCad = dtCad;
	}
	public String getDtMov() {
		return dtMov;
	}
	public void setDtMov(String dtMov) {
		this.dtMov = dtMov;
	}
	public String getComoConheceu() {
		return comoConheceu;
	}
	public void setComoConheceu(String comoConheceu) {
		this.comoConheceu = comoConheceu;
	}
	public String getFaixaIdade() {
		return faixaIdade;
	}
	public void setFaixaIdade(String faixaIdade) {
		this.faixaIdade = faixaIdade;
	}
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}
	public String getVlrDepositoBrl() {
		return vlrDepositoBrl;
	}
	public void setVlrDepositoBrl(String vlrDepositoBrl) {
		this.vlrDepositoBrl = vlrDepositoBrl;
	}
	public String getVlrSaqueBrl() {
		return vlrSaqueBrl;
	}
	public void setVlrSaqueBrl(String vlrSaqueBrl) {
		this.vlrSaqueBrl = vlrSaqueBrl;
	}
	public String getVlrTradeBrl() {
		return vlrTradeBrl;
	}
	public void setVlrTradeBrl(String vlrTradeBrl) {
		this.vlrTradeBrl = vlrTradeBrl;
	}
	public String getVlrDepositoFeeBrl() {
		return vlrDepositoFeeBrl;
	}
	public void setVlrDepositoFeeBrl(String vlrDepositoFeeBrl) {
		this.vlrDepositoFeeBrl = vlrDepositoFeeBrl;
	}
	public String getVlrSaqueFeeBrl() {
		return vlrSaqueFeeBrl;
	}
	public void setVlrSaqueFeeBrl(String vlrSaqueFeeBrl) {
		this.vlrSaqueFeeBrl = vlrSaqueFeeBrl;
	}
	public String getVlrTradeFeeBrl() {
		return vlrTradeFeeBrl;
	}
	public void setVlrTradeFeeBrl(String vlrTradeFeeBrl) {
		this.vlrTradeFeeBrl = vlrTradeFeeBrl;
	}
	
	
	

	
	
}
