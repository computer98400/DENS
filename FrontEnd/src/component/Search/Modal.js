import react, { useEffect } from 'react';
import '../../css/modal.css';
import { TagCloud } from 'react-tagcloud';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import axios from 'axios';
import { API_BASE_URL } from '../../config';

export default function Modal(props) {
    const { open, close, header,content,id,gomessanger,profile_keyword } = props;
    const navigate = useNavigate();
    const [token] = useCookies();
    const [userid] = useCookies();
    const authAxios = axios.create({
        baseURL: API_BASE_URL,
        headers: {
          Authorization: `Bearer "${token.token}"`,
          withCredentials: true,
        },
    })
    const goMsg = () => {
        console.log(userid.myID);
        authAxios.post(`/chat/room/${userid.myID}/${id}`)
            .then((res) => {
                navigate(`/auth/messenger`)
             })
            .catch((error) => { console.log(error) });
    }
    return (
        <div className={ open ? 'openModal modal':'modal'}>
            {open ? (
                <section style={{left:'-50vw'}}>
                    <div>
                    <header>
                        {header}
                        <button className='close' onClick={close}>{' '} &times; {' '}</button>
                        </header>
                
                        {profile_keyword ? <TagCloud minSize={12} maxSize={30} tags={profile_keyword} /> : `설정된 태그가 없습니다`}
                    <main>{ content}</main>
                    <footer>
                        <button className="close" onClick={ close}>창닫기</button>
                        <button className="close" onClick={ goMsg}>팀장메신저</button>
                        </footer>
                    </div>
                </section>
            ): null}
        </div>
    )
}