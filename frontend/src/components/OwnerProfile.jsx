import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useParams, useNavigate } from "react-router-dom";
import { editdetails, getdetailbyid } from "../api/FuelStationOwnerServiceApi";

const OwnerProfile = () => {
  const [owner, setOwner] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    nicNo: "",
    address: "",
  });

  const { id } = useParams();
  const navigate = useNavigate();
  const [isEditing, setIsEditing] = useState(false);
  const [originalOwner, setOriginalOwner] = useState({});
  const [error, setError] = useState(null);

  const fetchOwnerData = async () => {
    try {
      const response = await getdetailbyid(id);
      setOwner(response.data);
    } catch (error) {
      console.error("Error fetching owner details:", error);
      setError("Failed to fetch owner details.");
    }
  };

  useEffect(() => {
    fetchOwnerData();
  }, [id]);

  const handleEditClick = () => {
    setOriginalOwner({ ...owner });
    setIsEditing(true);
  };

  const handleSaveClick = async () => {
    if (!owner.name || !owner.email || !owner.phoneNumber || !owner.nicNo) {
      alert("Please fill in all fields before saving.");
      return;
    }
    if (window.confirm("Are you sure you want to update your profile?")) {
      try {
        await editdetails(id, owner);
        alert("Profile updated successfully!");
        setIsEditing(false);
      } catch (error) {
        console.error("Error updating profile:", error);
        setError("Failed to update profile.");
      }
    }
  };

  const handleCancelClick = () => {
    setOwner({ ...originalOwner });
    setIsEditing(false);
  };

  const isSaveDisabled = JSON.stringify(owner) === JSON.stringify(originalOwner);

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
                  { label: "Name", value: owner.name, field: "name" },
                  { label: "Email", value: owner.email, field: "email" },
                  { label: "Phone", value: owner.phoneNumber, field: "phoneNumber" },
                  { label: "NIC Number", value: owner.nicNo, field: "nicNo" },
                  { label: "Address", value: owner.address, field: "address" },
                ].map(({ label, value, field }) => (
                  <div className="mb-3" key={field}>
                    <label htmlFor={field} className="form-label">
                      {label}
                    </label>
                    <input
                      type="text"
                      id={field}
                      className={`form-control ${!isEditing && "bg-light"}`}
                      value={value}
                      onChange={(e) =>
                        setOwner({ ...owner, [field]: e.target.value })
                      }
                      readOnly={field === "nicNo" || !isEditing}
                    />
                  </div>
                ))}

                <div className="mt-4">
                  {isEditing ? (
                    <>
                      <button
                        type="button"
                        className="btn btn-success w-100 mb-2"
                        onClick={handleSaveClick}
                        disabled={isSaveDisabled}
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