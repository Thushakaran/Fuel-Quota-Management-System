// const FuelStationTable = ({ fuelStations, onDelete, onEdit, onToggleStatus }) 
const FuelStationTable = ({ fuelStations, onDelete, onEdit})=> {
    return (
        <table className="table table-striped table-bordered table-hover">
            <thead className="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Location</th>
                    <th>Station Name</th>
                    <th>Registration Number</th>
                    <th>Fuel Inventory</th>
                    <th>Status</th>
                    {/* <th>Edit</th> */}
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                {fuelStations.length === 0 ? (
                    <tr>
                        <td colSpan="8">No fuel stations found</td>
                    </tr>
                ) : (
                    fuelStations.map((station) => (
                        <tr key={station.id}>
                            <td>{station.id}</td>
                            <td>{station.location}</td>
                            <td>{station.stationName}</td>
                            <td>{station.registrationNumber}</td>
                            <td>
                                {station.fuelInventory
                                    ? Object.entries(station.fuelInventory).map(([fuelType, qty]) => (
                                        <div key={fuelType}>
                                            <strong>{fuelType}:</strong> {qty} liters
                                        </div>
                                    ))
                                    : "No data"}
                            </td>
                            {console.log(station.active)}
                            {/* <td>
                                <button
                                    className={`btn btn-sm ${station.active ? "btn-success" : "btn-secondary"}`}
                                    onClick={() => onToggleStatus(station.id)}
                                >
                                    {station.active ? "Active" : "Deactive"}
                                </button>
                            </td> */}


                            <td>
                                <button className="btn btn-primary btn-sm" onClick={() => onEdit(station)}>
                                    Edit
                                </button>
                            </td>
                            <td>
                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => {
                                        if (window.confirm("Are you sure you want to delete this fuel station?")) {
                                            onDelete(station.id);
                                        }
                                    }}
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
