import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useParams, useNavigate } from "react-router-dom";
import { editdetails, getdetailbyid } from "../api/FuelStationOwnerServiceApi";

const ProfileInput = ({ label, value, field, isEditing, onChange, readOnly }) => (
  <div className="mb-3">
    <label htmlFor={field} className="form-label">{label}</label>
    <input
      type="text"
      id={field}
      className={`form-control ${!isEditing && "bg-light"}`}
      value={value}
      onChange={onChange}
      readOnly={readOnly || !isEditing}
    />
  </div>
);

const OwnerProfile = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  
  const [owner, setOwner] = useState({
    name: "", email: "", phoneNumber: "", nicNo: "", address: ""
  });
  const [originalOwner, setOriginalOwner] = useState({});
  const [isEditing, setIsEditing] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchOwnerData = async () => {
      try {
        const response = await getdetailbyid(id);
        setOwner(response.data);
        setOriginalOwner(response.data);
      } catch (error) {
        console.error("Error fetching owner details:", error);
        setError("Failed to fetch owner details.");
      }
    };
    fetchOwnerData();
  }, [id]);

  const handleEditClick = () => setIsEditing(true);
  const handleCancelClick = () => {
    setOwner({ ...originalOwner });
    setIsEditing(false);
  };

  const validateInputs = () => {
    const nicRegex = /^\d{10}(\d{2})?$/;
    const phoneRegex = /^\d{10}$/;

    if (!owner.name || !owner.email || !owner.phoneNumber || !owner.nicNo) {
      return "All fields must be filled.";
    }
    if (!nicRegex.test(owner.nicNo)) {
      return "NIC Number must be 10 or 12 digits.";
    }
    if (!phoneRegex.test(owner.phoneNumber)) {
      return "Phone Number must be exactly 10 digits.";
    }
    return null;
  };

  const handleSaveClick = async () => {
    const validationError = validateInputs();
    if (validationError) {
      alert(validationError);
      return;
    }

    if (JSON.stringify(owner) === JSON.stringify(originalOwner)) {
      alert("No changes detected.");
      return;
    }

    if (window.confirm("Are you sure you want to update your profile?")) {
      try {
        await editdetails(id, owner);
        alert("Profile updated successfully!");
        setOriginalOwner({ ...owner });
        setIsEditing(false);
      } catch (error) {
        console.error("Error updating profile:", error);
        setError("Failed to update profile.");
      }
    }
  };

  return (
    <div className="container mt-5">
      <div className="text-center mb-4">
        <h1 className="fw-bold">Owner Profile</h1>
        <p className="text-muted">Update your personal information</p>
      </div>

      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-sm">
            <div className="card-header bg-primary text-white">
              <h3>Profile Details</h3>
            </div>
            <div className="card-body">
              {error && <div className="alert alert-danger">{error}</div>}

              <form>
                {[
                  { label: "Name", field: "name" },
                  { label: "Email", field: "email" },
                  { label: "Phone", field: "phoneNumber" },
                  { label: "NIC Number", field: "nicNo", readOnly: true },
                  { label: "Address", field: "address" },
                ].map(({ label, field, readOnly }) => (
                  <ProfileInput
                    key={field}
                    label={label}
                    value={owner[field]}
                    field={field}
                    isEditing={isEditing}
                    readOnly={readOnly}
                    onChange={(e) => setOwner({ ...owner, [field]: e.target.value })}
                  />
                ))}

                <div className="mt-4">
                  {isEditing ? (
                    <>
                      <button
                        type="button"
                        className="btn btn-success w-100 mb-2"
                        onClick={handleSaveClick}
                      >
                        Save Changes
                      </button>
                      <button
                        type="button"
                        className="btn btn-secondary w-100"
                        onClick={handleCancelClick}
                      >
                        Cancel
                      </button>
                    </>
                  ) : (
                    <>
                      <button
                        type="button"
                        className="btn btn-primary w-100 mb-2"
                        onClick={handleEditClick}
                      >
                        Edit Profile
                      </button>
                      <button
                        type="button"
                        className="btn btn-secondary w-100"
                        onClick={() => navigate(-1)}
                      >
                        Back
                      </button>
                    </>
                  )}
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OwnerProfile;
