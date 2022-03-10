import React from 'react'
// import '../../css/profile.css'
export default function ProfileGit({ edit, gitId, onSave }) {
  return (
    <div className="card mb-3">
      <div className="card-body">
        {!edit ? (
          <img
            src={`https://ghchart.rshah.org/${gitId} `}
            className="gitImage "
          />
        ) : (
          <></>
        )}
      </div>
    </div>
  )
}
