import React from 'react'
import '../../css/profile.css'
export default function ProfileGit({ edit, gitId, onSave }) {
  return (
    <div class="card mb-3">
      <div class="card-body">
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
