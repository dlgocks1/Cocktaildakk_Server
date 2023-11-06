package com.falco.cocktaildakkapi.admin.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.falco.cocktaildakkapi.admin.dto.UploadCocktailReq
import com.falco.cocktaildakkdomain.cocktail.model.Cocktail
import com.falco.cocktaildakkdomain.cocktail.model.CocktailImageType
import com.falco.cocktaildakkdomain.cocktail.repository.CocktailRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AdminService(
    private val awsS3Client: AmazonS3,
    private val cocktailRepository: CocktailRepository
) {

    val bucket = "cocktaildakk-s3"
    fun uploadCocktail(
        uploadCocktailReq: UploadCocktailReq
    ): Cocktail {
        val imageUrl = uploadImage(uploadCocktailReq.image, CocktailImageType.IMAGE)
        val listImageUrl = uploadImage(uploadCocktailReq.listImage, CocktailImageType.LIST)
        return cocktailRepository.save(
            uploadCocktailReq.convertToCocktail(
                iamgeUrl = imageUrl,
                listImageUrl = listImageUrl
            )
        )
    }

    private fun uploadImage(image: MultipartFile, type: CocktailImageType): String {
        val bytes = image.bytes
        val objectKey = type.directory + "/" + image.originalFilename // S3에 저장할 경로와 파일 이름
        val objMeta = ObjectMetadata().apply {
            contentType = "image/png"
            contentLength = image.inputStream.available().toLong()
        }
        awsS3Client.putObject(
            PutObjectRequest(bucket, objectKey, bytes.inputStream(), objMeta)
                .withCannedAcl(CannedAccessControlList.PublicRead) // PublicRead 권한으로 업로드 됨
        )
        return awsS3Client.getUrl(bucket, objectKey).toString()
    }

}