import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import ProductForm from './ProductForm';

const ProductEdit = () => {

    const convertUrlToFile = async (url, fileName) => {
        const response = await fetch(url);
        const blob = await response.blob();
        const file = new File([blob], fileName, { type: blob.type });
        return file;
      };


  const { productId } = useParams();
  const [initialData, setInitialData] = useState(null);
  //console.log("initialData:",initialData);
  const [images, setImages] = useState([]);
  //const [detailImage, setDetailImage] = useState([]);   //images가 자꾸 배열이 아닌 다른거로 넘어오는것 같아서 새로 만듦
  //console.log("images:",images);  //들어옴

  //상품 수정시에 저장되있던 값 넣어줌
  useEffect(() => {
    axios
      .get(`http://localhost:8989/product/${productId}`)
      .then((response) => {
        console.log("response.data.data.:",response.data.data);
        setInitialData(response.data.data.product);  //백에서 product안에 상세이미지도 담겨서 넘어옴
        setImages(response.data.data.images);
      })
      .catch((error) => {
        console.error('Error fetching product details:', error);
      });
  }, [productId]);

    


    const handleEdit = async  (productData, mainImg, imgList) => {
        const formData = new FormData();
  
        // 상품 정보에서 상세 이미지 데이터 제거
        const productInfo = { ...productData };
        delete productInfo.productImages; // 상세 이미지 제거
        delete productInfo.createdDate; // 상세 이미지 제거

        formData.append('product', JSON.stringify(productInfo));
        console.log("productInfo",productInfo);

        // 썸네일 이미지 추가
        if (mainImg instanceof File) {
            formData.append('upfile', mainImg); // 파일 객체인 경우 FormData에 추가
            console.log("mainImg",mainImg);
        }


        // 상세 이미지 추가
        const promises = imgList.map((imageFile) => {
            return new Promise((resolve, reject) => {
            if (imageFile.originFile instanceof File) {  //imageFile.originFile이 실제 파일 객체라면 (이미 사용자가 직접 업로드한 파일)
                formData.append('images', imageFile.originFile); // 원본 파일 추가
                resolve();
            } else if (imageFile.previewUrl) {      // (originFile이 없고, previewUrl이 있는 경우)
                // URL을 File 객체로 변환하여 추가
                convertUrlToFile(imageFile.previewUrl, `image-${imageFile.id}.jpg`)
                .then((file) => {
                    formData.append('images', file); // 변환된 파일 추가
                    //formData.append('imageIds', imageFile.id); // id 추가
                    resolve();
                })
                .catch((error) => {
                    console.error('Error converting URL to file:', error);
                    reject(error);
                });
                
            }
            console.log("imgList.id",imageFile.id);
            });

           
        });
        
        console.log("imgList",imgList);
            try {
                await Promise.all(promises);  // 모든 비동기 작업이 끝날 때까지 기다림
                
            
                // Axios 요청
                axios
                  .put(`http://localhost:8989/product/${productId}`, formData, {
                    headers: { 'Content-Type': 'multipart/form-data' },
                  })
                  .then((response) => {
                    alert('상품이 수정되었습니다!');
                    console.log("response:",response);
                  })
                  .catch((error) => {
                    console.error('Error updating product:', error);
                  });
              } catch (error) {
                console.error('Error processing images:', error);
              }
    };

  if (!initialData) return <div>Loading...</div>;

  return (
    <div>
      <h2>상품 수정</h2>
      <ProductForm images={images} initialData={initialData} method="PUT" onSubmit={handleEdit}/>
    </div>
  );
};

export default ProductEdit;
