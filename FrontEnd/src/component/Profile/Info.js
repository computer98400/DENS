import React, { useEffect } from 'react'
import { Button } from 'react-bootstrap'
export default function ProfileInfo({
  name,
  edit,
  position,
  stack,
  email,
  idCheck,
  gitId,
  onUpdate,
  onEdit,
  onSave,
}) {
  useEffect(() => {}, [idCheck])
  return (
    <>
      <div class="card mb-3">
        <div className="card-body">
          <div className="row">
            <div className="col-sm-3">
              <h6 className="mb-0">Full Name</h6>
            </div>
            <div className="col-sm-9 text-secondary">{name}</div>
          </div>
          <hr />
          <div className="row">
            <div className="col-sm-3">
              <h6 className="mb-0">E-mail</h6>
            </div>
            <div className="col-sm-9 text-secondary">{email}</div>
          </div>
          <hr />
          <div className="row">
            <div className="col-sm-3">
              <h6 className="mb-0">Position</h6>
            </div>
            <div className="col-sm-9 text-secondary">
              {edit ? (
                <input
                  onChange={onSave}
                  name="position"
                  value={position}
                ></input>
              ) : (
                position
              )}
            </div>
          </div>
          <hr />
          <div className="row">
            <div className="col-sm-3">
              <h6 className="mb-0">Stack</h6>
            </div>
            <div className="col-sm-9 text-secondary">
              {edit ? (
                <input onChange={onSave} name="stack" value={stack}></input>
              ) : (
                stack
              )}
            </div>
          </div>
          <hr />
          <div className="row">
            <div className="col-sm-3">
              <h6 className="mb-0">Git ID</h6>
            </div>
            <div className="col-sm-9 text-secondary">
              {edit ? (
                <>
                  <input name="gitId" value={gitId} onChange={onSave}></input>
                </>
              ) : (
                gitId
              )}
            </div>
          </div>
          <hr />
          {idCheck ? (
            <div className="row">
              <div className="col-sm-12">
                <div>
                  {edit ? (
                    <Button onClick={onUpdate} size="sm" variant="secondary">
                      확인
                    </Button>
                  ) : (
                    <Button onClick={onEdit} size="sm" variant="secondary">
                      편집
                    </Button>
                  )}
                </div>
              </div>
            </div>
          ) : (
            <div></div>
          )}
        </div>
      </div>
    </>
  )
}
