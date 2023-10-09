package com.example.fureverhomes_project.component;

import com.example.fureverhomes_project.dto.FileDTO;
import com.example.fureverhomes_project.entity.File;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {

    public List<File> parseFileInfo(List<MultipartFile> multipartFiles) throws Exception{
        List<File> fileList = new ArrayList<>(); //반환할 파일 리스트

        //파일이 존재할 경우
        if (!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            //프로젝트 디렉터 내의 저장을 위한 절대 경로 설정
            String absolutePath = new java.io.File("").getAbsolutePath() + java.io.File.separator + java.io.File.separator;

            //파일을 저장할 세부 경로 지정
            String dbpath = "/board_images" + java.io.File.separator + current_date;
            String path = "src/main/resources/static/board_images" + java.io.File.separator + current_date;
            java.io.File file = new java.io.File(path);

            //디렉터가 존재하지 않을 경우
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdir();

                if(!wasSuccessful) System.out.println("파일 디렉터 생성 실패");
            }

            //다중 파일 처리
            for (MultipartFile multipartFile : multipartFiles) {
                //파일 확장자 추출
                String originalExtension;
                String contentType = multipartFile.getContentType();

                //확장자명이 존재하지 않을 경우는 처리하지 않음
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {
                    if(contentType.contains("image/jpeg"))
                        originalExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalExtension = ".png";
                    else if(contentType.contains("image/jpg"))
                        originalExtension = ".jpg";
                    else break;
                }

                //저장 파일명 생성
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                String save_name = uuid + originalExtension;

                //파일 DTO 생성
                FileDTO fileDTO = new FileDTO(multipartFile.getOriginalFilename(), save_name, dbpath, multipartFile.getSize());

                //파일 엔티티 생성
                File fileEntity = File.builder()
                        .original_name(fileDTO.getOriginalFileName())
                        .save_name(fileDTO.getSaveFileName())
                        .file_path(fileDTO.getFilePath())
                        .size(fileDTO.getFileSize()).build();

                //생성후 리스트 추가
                fileList.add(fileEntity);

                //지정한 경로에 저장
                file = new java.io.File(absolutePath + path + java.io.File.separator + save_name);
                multipartFile.transferTo(file);

                //파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return fileList;
    }

    public void deleteFile(File file) {
        String absolutePath = new java.io.File("").getAbsolutePath() + java.io.File.separator + java.io.File.separator;
        String drPath = absolutePath + "src/main/resources/static" + java.io.File.separator + file.getFile_path();
        String path = drPath + java.io.File.separator + file.getSave_name();
        java.io.File targetFile = new java.io.File(path);
        java.io.File targetDr = new java.io.File(drPath);

        if (targetFile.exists()) {
            targetFile.delete();
        }
        if (!targetDr.exists()) {
            targetDr.delete();
        }
    }
}
