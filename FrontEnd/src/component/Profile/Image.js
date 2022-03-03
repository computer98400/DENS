import React, { useEffect, useState } from 'react'
import { Image } from 'react-bootstrap'
import { useNavigate } from 'react-router-dom'
import { store } from '../..'
export default function ProfileImage({
  id,
  fileImage,
  authAxios,
  idCheck,
  onLoad,
  onImageUpload,
}) {
  const [image, setImage] = useState('')
  const nav = useNavigate()
  const userId = store.getState().user.profileid

  useEffect(() => {
    authAxios
      .get(`/profile/image/${id}`, {
        responseType: 'blob',
      })
      .then((res) => {
        const url = window.URL.createObjectURL(
          new Blob([res.data], { type: res.headers['content-type'] })
        )
        setImage(url)
      })
      .catch((error) => console.log(error))
  }, [])
  function onMessageSend() {
    authAxios
      .post(`/chat/room/${userId}/${id}`)
      .then((res) => {
        authAxios
          .get(`/chat/room/enter/${res.data.roomId}/${userId}`)
          .then((res) => {
            return nav(`/auth/messenger/${res.data.roomId}`)
          })
          .catch(() => {
            return nav(`/auth/messenger`)
          })
      })
      .catch(() => {
        return nav(`/auth/messenger`)
      })
  }
  return (
    <div className="card">
      <div className="card-body">
        <div className="d-flex flex-column align-items-center text-center">
          {fileImage ? (
            <div>
              <Image
                alt="sample"
                src={fileImage}
                width={180}
                height={160}
                thumbnail
              />
            </div>
          ) : (
            <div>
              {image ? (
                <Image width={180} height={160} src={image} roundedCircle />
              ) : (
                <Image
                  width={180}
                  height={160}
                  src={require('./profile_default.png')}
                  roundedCircle
                />
              )}
            </div>
          )}

          <div className="mt-3">
            {idCheck ? (
              <div>
                <div>
                  <input
                    id="imgInput"
                    name="image"
                    type="file"
                    accept="image/*"
                    style={{ display: 'none' }}
                    onChange={onLoad}
                  ></input>
                  {!fileImage && (
                    <label
                      class="btn btn-outline-primary"
                      name="ImgBtn"
                      htmlFor="imgInput"
                    >
                      프로필 사진 등록
                    </label>
                  )}
                </div>
                {fileImage && (
                  <button
                    class="btn btn-primary"
                    onClick={onImageUpload}
                    size="sm"
                  >
                    저장하기
                  </button>
                )}
              </div>
            ) : (
              <div>
                <button className="btn btn-primary" onClick={onMessageSend}>
                  메세지
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
