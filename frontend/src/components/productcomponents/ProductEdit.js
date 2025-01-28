import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import ProductForm from '@components/productcomponents/ProductForm';

const ProductEdit = () => {



  const convertUrlToFile = async (url, fileName) => {  //url, fileName을 받아 파일객체로 변환해주는 함수
    const response = await fetch(url);
    const blob = await response.blob();
    const file = new File([blob], fileName, { type: blob.type });
    return file;
  };

  const { productId } = useParams();
  const [initialData, setInitialData] = useState(null);
  const [images, setImages] = useState([]);

  const [updateImg, setUpdateImg] = useState([]);
  const [thumbnailId, setThumbnailId] = useState(null);
  const [mainImg, setMainImg] = useState(null);

 
  
  //상품 수정시에 저장되있던 값 넣어줌
  useEffect(() => {
    axios
      .get(`http://localhost:8989/product/${productId}`)
      .then((response) => {
        console.log("response.data.data.:",response.data.data);
        console.log("response.data.data.:",response.data.data.images);
        setThumbnailId((response.data.data.product.thumbnailImage).split('/').pop().split('_')[0]); // 초기 썸네일 ID 설정
        console.log("썸네일 아이디",(response.data.data.product.thumbnailImage).split('/').pop().split('_')[0]);
        
        setMainImg(response.data.data.product.thumbnailImage);

        setInitialData(response.data.data.product);  //백에서 product안에 상세이미지도 담겨서 넘어옴
        setImages(response.data.data.images);
      })
      .catch((error) => {
        console.error('Error fetching product details:', error);
      });
  }, [productId]);

    const handleEdit = async  (productData, mainImg, imgList,  deleteImg) => {
      const formData = new FormData();
      //setDeleteImg(deleteImg);

      // 백에 넘길 상품 정보에서 이미지, 날짜 데이터 제거 (productInfo에서는 글 정보만 넘길 것)
      const productInfo = { ...productData };
      delete productInfo.thumbnailImage; 
      delete productInfo.productImages; 
      delete productInfo.createdDate; 

      formData.append('product', JSON.stringify(productInfo));
      console.log("productInfo",productInfo);

      
/*
      // formData에 썸네일 파일객체 추가
      if (mainImg instanceof File) {
        // 썸네일 이미지가 변경된 경우
        formData.append('upfile', mainImg); // 새로운 썸네일 추가
        console.log("새로운 썸네일 파일:", mainImg);
      
        // 기존 썸네일 이미지 ID를 삭제 목록에 추가
        if (currentThumbnailId) {
          deleteImg.push(currentThumbnailId);
          console.log("삭제할 썸네일 ID:", currentThumbnailId);
        }
      }
*/



// 새로운 썸네일 파일 추가
if (mainImg instanceof File) {
  formData.append('upfile', mainImg);
}

      //const updatedDeleteImg = [...deleteImg];
      const updateImg =[];

      const oldList = [...imgList];
      console.log("순회하기직전 이미지들: ",imgList);
      const promises = imgList.map((imageFile) => {
        if (imageFile.originFile != oldList.originFile) {
          console.log("oldList.originFile:", oldList.originFile);
  
          console.log("변경됨:", imageFile);
          //setDeleteList(imageFile.id);
  
          if (typeof imageFile.id === "number"){
            updateImg.push(imageFile.id);
          }
          else{
            //setImages(imageFile.id);
          }
          //console.log("updatedDeleteImg 담기나:", updatedDeleteImg);
          return Promise.resolve({ file: imageFile.originFile, id: imageFile.id });
  
        } else if (imageFile.previewUrl) {
          console.log("변경안됨:", imageFile);
          return convertUrlToFile(imageFile.previewUrl, `image-${imageFile.id}.jpg`)
            .then((file) => ({ file, id: imageFile.id }))
            .catch((error) => {
              console.error(`Error converting URL to file for image ID ${imageFile.id}:`, error);
              return null;
            });
        }
      });

      //전달받은 삭제이미지 배열 같이 append
      formData.append('updateImg', JSON.stringify(updateImg));
      formData.append('deleteImg', JSON.stringify(deleteImg));
      console.log('전달보낸 deleteImg : ', deleteImg);
      console.log('전달보낸 updateImg : ', updateImg);


      
      // promises배열의 모든 Promise가 완료된 후 순서대로 처리
      /*
      Promise.all(promises)
        .then((results) => {
            results.forEach((result) => {
                formData.append('images', result.file); // 원래 순서대로 상세이미지를 append
                console.log("Appending image with ID:", result.id);
            });
        })
        .catch((error) => {
            console.error('Error processing images:', error);
        });
*/
        console.log("FormData 확인:");
        for (let pair of formData.entries()) {
            console.log(`${pair[0]}:`, pair[1]);
        }

      console.log("데이터 보내기 직전 imgList : ", imgList);

      // 이미지 리스트를 displayOrder 기준으로 정렬
      const sortedImgList = [...imgList]
      .sort((a, b) => a.displayOrder - b.displayOrder) // displayOrder 기준으로 정렬
      .map((img, index) => ({ ...img, displayOrder: index })); // 정렬 후 displayOrder 재설정

      console.log('Submitting sorted images:', sortedImgList);

      try {
          // 모든 비동기 작업이 끝날 때까지 기다림
          const results = await Promise.all(promises);
      
          results.forEach((result, index) => {
            if (result && result.file) {
              const sortedImage = sortedImgList[index]; // 정렬된 순서에 맞게 가져오기
              formData.append('images', result.file);
              //formData.append(`imageDisplayOrder[${index}]`, sortedImage.displayOrder); // displayOrder도 함께 추가
              console.log("Appending image with ID:", sortedImage.id, "and displayOrder:", sortedImage.displayOrder);
            }
          });
      
          //console.log([...formData.entries()]); // formData 내용 확인
      
          // Axios 요청
          const response = await axios.put(
              `http://localhost:8989/product/admin/${productId}`,
              formData
          );
      
          alert('상품이 수정되었습니다!');
          console.log("response:", response);
      } catch (error) {
          console.error('Error updating product:', error);
      }
    };

  if (!initialData) return <div>Loading...</div>;

  return (
    <div>
      <h2>상품 수정</h2>
      <ProductForm images={images} initialData={initialData} thumbnailId={thumbnailId} method="PUT" onSubmit={handleEdit}/>
    </div>
  );
};

export default ProductEdit;