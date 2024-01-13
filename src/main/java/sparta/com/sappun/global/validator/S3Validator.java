package sparta.com.sappun.global.validator;

import static sparta.com.sappun.global.response.ResultCode.INVALID_PROFILE_IMAGE_FILE;
import static sparta.com.sappun.global.response.ResultCode.NULL_FILE_TYPE;

import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.global.exception.GlobalException;

public class S3Validator {

    private static final String IMAGE_JPG = "image/jpeg";
    private static final String IMAGE_PNG = "image/png";

    public static void isProfileImageFile(MultipartFile multipartFile) {
        // 업로드된 파일의 컨텐츠 타입 가져오기
        String fileType = multipartFile.getContentType();

        // 파일 타입이 유효하지 않은 경우 예외 발생
        if (isNullFileType(fileType)) {
            throw new GlobalException(NULL_FILE_TYPE);
        }
        // 이미지 파일이 아닌 경우 예외 발생
        if (!isImageFile(fileType)) {
            throw new GlobalException(INVALID_PROFILE_IMAGE_FILE);
        }
    }

    private static boolean isNullFileType(String fileType) {
        return fileType == null;
    }

    private static boolean isImageFile(String fileType) {
        // 파일의 컨텐츠 타입이 이미지 파일(JPEG or PNG)인지 여부를 판단
        return fileType.equals(IMAGE_JPG) || fileType.equals(IMAGE_PNG);
    }
}
