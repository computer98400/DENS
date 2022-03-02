import React, { useEffect, useState } from 'react';
import UserCard from './UserCard';
import Modal from './Modal';
import styled from 'styled-components';
import { keywordCheck } from '../../api/search';
export default function UserList(props) {
    const [temp, setTemp] = useState([]);
    
    useEffect(() => {
        setTemp(props.userlist);
    });

    const [modalppen, setModalppen] = useState(false);

    const [modaltitle, setModaltitle] = useState("");
    const [modalcontent, setModalcontent] = useState("");
    const [modalprofilekeyword, setModalprofilekeyword] = useState([]);
    const [modalID, setmodalID] = useState("");
    const openModal = (id, title, content) => {
        setmodalID(id);
        setModalppen(true);
        setModaltitle(title);
        setModalcontent(content);
        setModalprofilekeyword([]);
        keywordCheck(id, (response) => {
            response.data.profile_keyword.map((data) => {
                const xxx = {
                    value: data.name,
                    count: data.count,
                }
                setModalprofilekeyword([
                    ...modalprofilekeyword,
                    xxx,
                ]);
            })
        }, (error) => {
            console.log(error)
        });
        
        // console.log(test);
        //console.log(modalprofilekeyword);
    }
    const closeModal = () => {
        setModalppen(false);
    }

    return (
        <ContainerT>
            <SectionName>회원님과 연관될만한 사람리스트</SectionName>
            <SearchBigBox>
                <>
                    {modalppen ? <Modal open={modalppen} close={closeModal} id={modalID}  profile_keyword={modalprofilekeyword} content={modalcontent} header={modaltitle}>{modaltitle}</Modal> : ``}
                </>
                    {temp.map((data) => {return <UserCard  click={openModal} check= {data.profile_id} data={data}/>}) }
            </SearchBigBox>
        </ContainerT>
    )
}

const ContainerT = styled.div`
    display : flex;
    flex-direction : column;
    align-items: center;
`
const SearchBigBox = styled.div`
    margin-left: 100px;
    display: flex;
    background-color: white;
    border: 1px solid pick;
    border-radius : 5px;
    box-shadow : 0px 30px 40px #f18fa1;
    flex-flow: column nowrap;
    width: 30vw;
    height: 76vh;
    margin: 20px;
    overflow: scroll;
    &::-webkit-scrollbar{
        display:none;
    }
`
const SectionName = styled.h3`
font-family : 'Cafe24Ssurround';
color: grey;
text-shadow: 3px 3px #ccc; 
`