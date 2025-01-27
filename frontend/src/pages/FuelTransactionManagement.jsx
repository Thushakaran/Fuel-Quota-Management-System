import React, { useState, useEffect } from "react";
import axios from "../api/axiosInstance";
import FuelTransactionTable from "../components/FuelTransactionTable";
import FuelTransactionEditForm from "../components/FuelTransactionEditForm";
import AdminNavbar from "../components/AdminNavbar";
import AdminFooter from "../components/AdminFooter";

const FuelTransactionManagement = () => {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [editingTransaction, setEditingTransaction] = useState(null);

    // Load transactions when component mounts
    useEffect(() => {
        loadTransactions();
    }, []);

    // Fetch transactions from backend
    const loadTransactions = () => {
        setLoading(true);
        axios
            .get("/admin/transactions")
            .then((response) => {
                setTransactions(response.data || []);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Error loading transactions:", err);
                setError("Failed to load transactions");
                setLoading(false);
            });
    };

    // Handle delete action
    const handleDelete = (id) => {
        if (window.confirm("Are you sure you want to delete this transaction?")) {
            axios
                .delete(`/admin/transactions/${id}`)
                .then(() => {
                    setTransactions((prev) => prev.filter((transaction) => transaction.id !== id));
                })
                .catch(() => {
                    alert("Failed to delete transaction");
                });
        }
    };

    // Open the edit form for a specific transaction
    const handleEdit = (transaction) => {
        setEditingTransaction(transaction);
    };

    const handleSave = (updatedTransaction) => {
        axios
            .put(`/admin/transactions/${updatedTransaction.id}`, updatedTransaction)
            .then(() => {
                setTransactions((prev) =>
                    prev.map((transaction) =>
                        transaction.id === updatedTransaction.id
                            ? { ...transaction, ...updatedTransaction }
                            : transaction
                    )
                );
                setEditingTransaction(null);
            })
            .catch((error) => {
                console.error("Failed to save transaction:", error.response || error);
                alert(error.response?.data?.message || "Failed to save transaction");
            });
    };
    

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div>
            <AdminNavbar />
            <div className="container mt-5">
                <h2>Fuel Transactions</h2>
                <FuelTransactionTable
                    transactions={transactions}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                />
            </div>
            {editingTransaction && (
                <FuelTransactionEditForm
                    transaction={editingTransaction}
                    onSave={handleSave}
                    onClose={() => setEditingTransaction(null)}
                />
            )}
            <AdminFooter />
        </div>
    );
};

export default FuelTransactionManagement;
