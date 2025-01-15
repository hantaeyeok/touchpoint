import "@styles/Qna.css";
import React, {useContext, useEffect, useState } from "react";
import { FaqContext } from "@context/FaqContext";
import axios from "axios";
import Modal from "react-modal";
import { useNavigate } from "react-router-dom";


Modal.setAppElement("#root");

function FaqList() {

    const navigate = useNavigate(); 
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [deleteModalIsOpen, setDeleteModalIsOpen] = useState(false);
    const [selectedFaq, setSelectedFaq] = useState({ faqNo: '', faqTitle: '', answer: '' });
    const { FaqTodoList, setFaqTodoList } = useContext(FaqContext);

    useEffect(() => {
        const fetchFaqs = async () => {
            try {
                const response = await axios.get("http://localhost:8989/qna");
                setFaqTodoList(response.data); 
            } catch (error) {
                console.error("FAQ 데이터를 가져오는 중 오류 발생:", error);
            }
        };
        fetchFaqs();
    }, [setFaqTodoList]);


    
    const editOpenModal = (faq) => {
        setSelectedFaq(faq); 
        setModalIsOpen(true);
    };



    const editSubmit = async () => {
        if(!selectedFaq.faqTitle || !selectedFaq.answer){
            alert("제목과 내용을 입력해주세요")
            return;
        };

        try {
            const response = await axios.put(`http://localhost:8989/qna/update/${selectedFaq.faqNo}`, selectedFaq);
            console.log("서버 응답:", response.data);
            alert("글이 성공적으로 저장되었습니다!");
        } catch (error) {
        console.error("데이터 저장 중 오류 발생:", error.message);
        alert("글 저장 중 문제가 발생했습니다. 다시 시도해주세요.");
        };

        setFaqTodoList((prevFaqs) =>
            prevFaqs.map((faq) =>
                faq.faqNo === selectedFaq.faqNo ?  { ...faq, faqTitle: selectedFaq.faqTitle, answer: selectedFaq.answer } : faq
            )
        );

        console.log("저장된 데이터:", selectedFaq);
        setModalIsOpen(false);
        navigate("/faq");
    };

    const deleteOpenModal = (faq) => {
        setSelectedFaq(faq);
        setDeleteModalIsOpen(true);
    };

    const deleteSubmit = async () => {
        console.log("faqNo :", selectedFaq.faqNo );
        try {
            const response = await axios.delete(`http://localhost:8989/qna/delete/${selectedFaq.faqNo}`);
            console.log("서버 응답:", response.data);
            alert("글이 성공적으로 삭제되었습니다!");
        } catch (error) {
        console.error("데이터 저장 중 오류 발생:", error.message);
        alert("글 삭제 중 문제가 발생했습니다. 다시 시도해주세요.");
        };

        setFaqTodoList((prevFaqs) =>
            prevFaqs.filter((faq) => faq.faqNo !== selectedFaq.faqNo)
        );
        navigate("/faq");
        setDeleteModalIsOpen(false);
    };

    return(
        <div>
            {FaqTodoList.map((item,index)=>(

                <div className="faqBox1" key={index}>
                    <div className="faqBox2">
                        <span className="q-icon">Q .</span>
                        <span className="q-text">{item.faqTitle}</span>
                    </div>
                    <div className ="faq_answer">
                        <span className="a-icon">A.</span>
                        <div className="answer-text">{item.answer}</div>
                        <div className="answer-text">{item.faqNo}</div>
                        <div className="faq_btn">
                            <button className="faq_btn1" onClick={() => editOpenModal (item)}>수정하기</button>
                            <button className="faq_btn2" onClick={() => deleteOpenModal(item)}>삭제하기</button>
                        </div>
                    </div>
                </div>
            ))};
            <div className="modalBox">
                <Modal className="editModal"
                    isOpen={modalIsOpen}
                    onRequestClose={() => setModalIsOpen(false)}>
                    
                        <div className="qna-form-group">
                            <input 
                                    className="qnaInput"
                                    value={selectedFaq.faqTitle}
                                    onChange={(event)=>setSelectedFaq({ ...selectedFaq, faqTitle: event.target.value })}
                                    type="text" 
                                    id="title" 
                                    name="title" 
                                    placeholder="제목을 입력하세요" />
                        </div>
                        <div className="qna-form-group">
                            <textarea
                                    className="qnaTextarea"
                                    value={selectedFaq.answer}
                                    onChange={(event)=>setSelectedFaq({ ...selectedFaq, answer: event.target.value })}
                                    id="content" 
                                    name="content" 
                                    placeholder="내용을 입력하세요">
                            </textarea>
                        </div>
                        <button onClick={editSubmit}>저장</button>
                        <button onClick={() => setModalIsOpen(false)}>닫기</button>
                </Modal>
                <Modal className="deleteModal"
                    isOpen={deleteModalIsOpen}
                    onRequestClose={() => setDeleteModalIsOpen(false)}>
                    <p>삭제를 그대로 진행 하시겠습니까?</p>
                    <button onClick={deleteSubmit}>삭제</button>
                </Modal>
            </div>
        </div>
    )
}
export default FaqList