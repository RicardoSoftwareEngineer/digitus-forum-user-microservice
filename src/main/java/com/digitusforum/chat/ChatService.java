package com.digitusforum.chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.digitusforum.user.util.M;

@Service
public class ChatService {

	@Autowired
	ChatMessageRepository chatMessageRepository;

	@Autowired
	ChatSubjectRepository chatSubjectRepository;
	
	@Autowired
	FactDadosRepository dadosRepository;
	
	public ConversationVO sup(ChatMessageVO chatMessageVO) throws IOException{
		List<FactDadosEntity> factDadosList = new ArrayList<FactDadosEntity>();
		//System.out.println(new File("excel.xlsx"));
		//System.out.println(new File("").getCanonicalPath());
		//System.out.println(new File("").getPath());
		//System.out.println(new File("").list());
		//for(String file:new File(""s))
		FileInputStream file = new FileInputStream(new File("excel.xlsx"));
	    XSSFWorkbook workbook = new XSSFWorkbook(file);
	    XSSFSheet worksheet = workbook.getSheetAt(2);
	    
	    //for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
	    for(int i=chatMessageVO.getFrom();i<chatMessageVO.getTo() ;i++) {
	    	FactDadosEntity factDado = new FactDadosEntity();
	            
	    	try{
	    		XSSFRow row = worksheet.getRow(i);
		        factDado.setMemberId(row.getCell(0).getRawValue().replace(".", ""));
		        factDado.setEmpresa(row.getCell(1).getRawValue().replace(".", ""));
		        factDado.setDtCad(row.getCell(2).getRawValue().replace(".", ""));
		        factDado.setDtMov(row.getCell(3).getRawValue().replace(".", ""));
		        factDado.setComoConheceu(row.getCell(4).getRawValue().replace(".", ""));
		        factDado.setFaixaIdade(row.getCell(5).getRawValue().replace(".", ""));
		        factDado.setTipoPessoa(row.getCell(6).getRawValue().replace(".", ""));
		        factDado.setUf(row.getCell(7).getRawValue().replace(".", ""));
		        factDado.setNivel(row.getCell(8).getRawValue().replace(".", ""));
		        factDado.setEquipamento(row.getCell(9).getRawValue().replace(".", ""));
		        factDado.setIdMoeda(row.getCell(10).getRawValue().replace(".", ""));
		        factDado.setVlrDepositoBrl(row.getCell(11).getRawValue().replace(".", ""));
		        factDado.setVlrSaqueBrl(row.getCell(12).getRawValue().replace(".", ""));
		        factDado.setVlrTradeBrl(row.getCell(13).getRawValue().replace(".", ""));
		        factDado.setVlrDepositoFeeBrl(row.getCell(14).getRawValue().replace(".", ""));
		        factDado.setVlrSaqueFeeBrl(row.getCell(15).getRawValue().replace(".", ""));
		        factDado.setVlrTradeFeeBrl(row.getCell(16).getRawValue().replace(".", ""));
		        factDado = dadosRepository.save(factDado);
	    	}catch (Exception e) {
				// TODO: handle exception
			}
	        
	        
	            
	        //tempStudent.setId((int) row.getCell(0).getNumericCellValue());
	        //tempStudent.setContent(row.getCell(1).getStringCellValue());
	        //factDadosList.add(factDado); 
	        
	        
	    }
		System.out.println("do you run this right");
		return null;
	}

	public ConversationVO chat(ChatMessageVO chatMessageVO) {
		if (StringUtils.isBlank(chatMessageVO.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_USER_ID);
		if (StringUtils.isBlank(chatMessageVO.getUserEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_USER_EMAIL);
		if (StringUtils.isBlank(chatMessageVO.getUserType()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_USER_TYPE);
		if (StringUtils.isBlank(chatMessageVO.getMessage()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_MESSAGE);
		if (StringUtils.isBlank(chatMessageVO.getUserName()))
			chatMessageVO.setUserName(chatMessageVO.getUserEmail().substring(0, 7));

		ChatSubjectEntity subject = null;
		if (StringUtils.isBlank(chatMessageVO.getChatSubjectId())) {
			if (StringUtils.isBlank(chatMessageVO.getName()))
				chatMessageVO.setName("---");

			subject = new ModelMapper().map(chatMessageVO, ChatSubjectEntity.class);
			subject.setLastUpdated(ZonedDateTime.now());
			subject = chatSubjectRepository.save(subject);
		} else {
			subject = chatSubjectRepository.findByChatSubjectIdAndDeletedIsFalse(chatMessageVO.getChatSubjectId());
			if (subject == null)
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_SUBJECT_NOT_FOUND);
		}
		chatMessageVO.setChatSubjectId(subject.getChatSubjectId());
		ChatMessageEntity chat = new ChatMessageEntity();
		chat.setMessage(chatMessageVO.getMessage());
		chat.setUserId(chatMessageVO.getUserId());
		chat.setUserName(chatMessageVO.getUserName());
		chat.setChatSubjectId(chatMessageVO.getChatSubjectId());
		chat.setUserEmail(chatMessageVO.getUserEmail());
		chat.setUserType(chatMessageVO.getUserType());
		List<ChatMessageEntity> conversation = chatMessageRepository
				.findByChatSubjectIdOrderByPosition(chatMessageVO.getChatSubjectId());
		chat.setPosition(conversation.size());
		chat = chatMessageRepository.save(chat);
		// i know this is not performati but i will keep here to wait until get enough
		// messages to become slow
		// and test how much the database index speed it up //TODO
		return getConversation(chatMessageVO.getChatSubjectId());
	}

	public ConversationVO getConversation(ChatMessageVO chatMessageVO) {
		if (StringUtils.isBlank(chatMessageVO.getChatSubjectId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_SUBJECT_ID);

		return getConversation(chatMessageVO.getChatSubjectId());
	}
	
	public void rearrangeMessages(List<ChatMessageEntity> chats) {
		try {
			String actualPersonChatting = "";
			String lastPersonChatting = "";
			String lastAlignment = "";
			for(int i = 0; i < chats.size(); i++) {
				actualPersonChatting = chats.get(i).getUserEmail();
				if(!actualPersonChatting.equals(lastPersonChatting)) {
					changeAlignment(chats.get(i));
				}else {
					chats.get(i).setAlignment(lastAlignment);
				}
				lastAlignment = chats.get(i).getAlignment();	
				lastPersonChatting = chats.get(i).getUserEmail();
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//return chats;
	}
	
	private void changeAlignment(ChatMessageEntity chatMessageEntity) {
		if(StringUtils.isBlank(chatMessageEntity.getAlignment())) {
			chatMessageEntity.setAlignment("leftAlignment");
		}else if(chatMessageEntity.getAlignment().equals("leftAlignment")) {
			chatMessageEntity.setAlignment("rightAlignment");
		}else {
			chatMessageEntity.setAlignment("leftAlignment");
		}
	}

	public ConversationVO getConversation(String subjectId) {
		ChatSubjectEntity subject = chatSubjectRepository.findByChatSubjectIdAndDeletedIsFalse(subjectId);
		if (subject == null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_SUBJECT_NOT_FOUND);

		List<ChatMessageEntity> chats = chatMessageRepository.findByChatSubjectIdOrderByPosition(subjectId);
		rearrangeMessages(chats);
		List<ChatMessageVO> chatsVO = new ModelMapper().map(chats, List.class);

		ConversationVO conversation = new ConversationVO();
		conversation.setSubjectId(subject.getChatSubjectId());
		conversation.setConversation(chatsVO);
		conversation.setSubjectName(subject.getName());
		
		return conversation;
	}

	public List<ChatSubjectVO> getConversations(ChatMessageVO chatMessageVO) {
		if (StringUtils.isBlank(chatMessageVO.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_USER_ID);
		List<ChatSubjectEntity> conversationsEntity = chatSubjectRepository
				.findByUserIdAndDeletedIsFalse(chatMessageVO.getUserId());
		List<ChatSubjectVO> conversationsVO = new ModelMapper().map(conversationsEntity, List.class);
		return conversationsVO;
	}

}
































