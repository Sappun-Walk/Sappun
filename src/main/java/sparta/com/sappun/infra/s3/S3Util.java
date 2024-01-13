package sparta.com.sappun.infra.s3;

import static sparta.com.sappun.global.response.ResultCode.NOT_FOUND_FILE;
import static sparta.com.sappun.global.response.ResultCode.SYSTEM_ERROR;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.global.exception.GlobalException;

@Service
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Getter
    @RequiredArgsConstructor // final필드를 초기화하기 위해 사용
    public enum FilePath { // 파일 경로를 나타내는 상수를 정의
        PROFILE("profile/"),
        BOARD("board/"),
        COMMENT("comment/");

        private final String path; // 경로를 저장하는 final 필드
    }

    private static ObjectMetadata setObjectMetadata(MultipartFile multipartFile) {
        // ObjectMetadata 객체 생성
        ObjectMetadata metadata = new ObjectMetadata();
        // 업로드할 파일의 길이 설정
        metadata.setContentLength(multipartFile.getSize());
        // 업로드할 파일의 컨텐츠 타입 설정
        metadata.setContentType(multipartFile.getContentType());
        // 생성한 ObjectMetadata 객체 반환
        return metadata;
    }

    public String uploadFile(MultipartFile multipartFile, FilePath filePath) {
        // 업로드할 파일이 존재하지 않거나 비어있으면 null 반환
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        // 업로드할 파일의 고유한 파일명 생성
        String fileName = createFileName(multipartFile.getOriginalFilename());
        // 업로드할 파일의 메타데이터 생성
        ObjectMetadata metadata = setObjectMetadata(multipartFile);

        try {
            // S3에 파일 업로드
            amazonS3Client.putObject(
                    bucketName, filePath.getPath() + fileName, multipartFile.getInputStream(), metadata);
        } catch (Exception e) {
            // 업로드 중에 예외 발생 시 전역 예외(GlobalException) 발생
            throw new GlobalException(SYSTEM_ERROR);
        }
        // 업로드한 파일의 URL 반환
        return getFileUrl(fileName, filePath);
    }

    public void deleteFile(String fileUrl, FilePath filePath) {
        // 주어진 파일 URL로부터 파일명을 추출
        String fileName = getFileNameFromFileUrl(fileUrl, filePath);
        // 파일명이 비어있거나 해당 파일이 존재하지 않으면 예외 발생
        if (fileName.isBlank()
                || !amazonS3Client.doesObjectExist(bucketName, filePath.getPath() + fileName)) {
            throw new GlobalException(NOT_FOUND_FILE);
        }
        // S3에서 파일 삭제
        amazonS3Client.deleteObject(bucketName, filePath.getPath() + fileName);
    }

    public String getFileUrl(String fileName, FilePath filePath) {
        // AWS S3 클라이언트를 사용하여 주어진 버킷, 파일 경로 및 파일명에 해당하는 파일의 URL을 얻어옴
        return amazonS3Client.getUrl(bucketName, filePath.getPath() + fileName).toString();
    }

    private String getFileNameFromFileUrl(String fileUrl, FilePath filePath) {
        // 파일 URL에서 파일 경로 다음의 문자열부터 파일명의 끝까지 추출하여 반환
        return fileUrl.substring(fileUrl.lastIndexOf(filePath.getPath()) + filePath.getPath().length());
    }

    private String createFileName(String fileName) {
        // UUID를 사용하여 고유한 문자열을 생성하고, 주어진 파일명과 연결하여 반환
        return UUID.randomUUID().toString().concat(fileName);
    }
}
