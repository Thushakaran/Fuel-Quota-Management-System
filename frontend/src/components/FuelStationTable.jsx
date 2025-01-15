import React from "react";


const FuelStationTable = ({ fuelStations, onEdit, onDelete }) => {
    const fuelStationList = Array.isArray(fuelStations) ? fuelStations : [];

    return (
        <table className="table table-striped table-bordered table-hover">
            <thead className="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Location</th>
                    <th>Station Name</th>
                    <th>Registration Number</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                {fuelStationList.length === 0 ? (
                    <tr>
                        <td colSpan="6">No fuel stations found</td>
                    </tr>
                ) : (
                    fuelStationList.map((station) => (
                        <tr key={station.id}>
                            <td>{station.id}</td>
                            <td>{station.location}</td>
                            <td>{station.stationName}</td>
                            <td>{station.registrationNumber}</td>
                            <td>
                                <button
                                    className="btn btn-primary btn-sm"
                                    onClick={() => onEdit(station)}
                                >
                                    Edit
                                </button>
                            </td>
                            <td>
                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => onDelete(station.id)}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))
                )}
            </tbody>
        </table>
    );
};

export default FuelStationTable;
