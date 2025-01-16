import React, { createContext, useState } from "react";

// Context 생성
export const FaqContext = createContext();

// Provider 컴포넌트
export const FaqProvider = ({ children }) => {
    const [FaqTodoList, setFaqTodoList] = useState([]); 

    return (
        <FaqContext.Provider value={{ FaqTodoList, setFaqTodoList }}>
            {children}
        </FaqContext.Provider>
    );
};
