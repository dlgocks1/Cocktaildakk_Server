package com.falco.cocktaildakk.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.falco.cocktaildakk.controller.UploadCocktail
import com.falco.cocktaildakk.domain.cocktail.Cocktail
import com.falco.cocktaildakk.domain.cocktail.CocktailImageType
import com.falco.cocktaildakk.repository.CocktailRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class AdminService(
    private val awsS3Client: AmazonS3,
    private val cocktailRepository: CocktailRepository
) {

    val bucket = "cocktaildakk-s3"
    fun uploadCocktail(
        uploadCocktail: UploadCocktail
    ): Cocktail {
        val imageUrl = uploadImage(uploadCocktail.image, CocktailImageType.IMAGE)
        val listImageUrl = uploadImage(uploadCocktail.listImage, CocktailImageType.LIST)
        return cocktailRepository.save(
            uploadCocktail.convertToCocktail(
                iamgeUrl = imageUrl,
                listImageUrl = listImageUrl
            )
        )
    }

    fun uploadImage(image: MultipartFile, type: CocktailImageType): String {
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