import React from "react";

const FuelTransactionTable = ({ transactions, onEdit, onDelete }) => {
    const transactionList = Array.isArray(transactions) ? transactions : [];

    return (
        <table className="table table-striped table-bordered table-hover">
            <thead className="table-dark">
                <tr>
                    <th>Vehicle Number</th>
                    <th>Pumped Fuel (Amount)</th>
                    <th>Date</th>
                    <th>Station Name</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                {transactionList.length === 0 ? (
                    <tr>
                        <td colSpan="6">No transactions found</td>
                    </tr>
                ) : (
                    transactionList.map((transaction) => (
                        <tr key={transaction.id}>
                            <td>{transaction.vehicle?.vehicleNumber || "N/A"}</td>
                            <td>{transaction.amount} liters</td>
                            <td>{new Date(transaction.transactionDate).toLocaleString()}</td>
                            <td>{transaction.station?.stationName || "N/A"}</td>
                            <td>
                                <button
                                    className="btn btn-primary btn-sm"
                                    onClick={() => onEdit(transaction)}
                                >
                                    Edit
                                </button>
                            </td>
                            <td>
                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => onDelete(transaction.id)}
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

export default FuelTransactionTable;
