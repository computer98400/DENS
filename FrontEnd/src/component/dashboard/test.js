import React, { useEffect, useState } from 'react';
import { test22 } from '../../api/test';
export default function Testpage() {
    
    const [checkdata, setCheckdata] = useState({ test:"111"});
    
    console.log(checkdata);
    useEffect(() => {
        test22("가세요",
            (response) => {
            setCheckdata(JSON.stringify(response.data));
        },  
        (error) => {
        console.log("오류가 됨.", JSON.stringify(error));
    });
    }, []);

    
    return (
        <>
            <h3>test페이지입니다.</h3>
        </>
    )
}