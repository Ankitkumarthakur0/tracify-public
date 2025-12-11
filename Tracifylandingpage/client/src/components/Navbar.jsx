import React from 'react';
import './Navbar.css'; // We'll add specific styles if needed, or use global

const Navbar = ({ onProfileClick, user, onHomeClick }) => {
    return (
        <nav className="navbar glass-panel">
            <div className="navbar-left">
                <img src="/logoxy.png" alt="Tracify Logo" className="navbar-logo" />
                <span className="navbar-brand">TRACIFY</span>
            </div>
            <div className="navbar-right">
                <button className="home-btn glass-btn" onClick={onHomeClick}>HOME</button>
                <button className="profile-btn glass-btn" onClick={onProfileClick}>
                    <div className="profile-icon">
                        {user && user.photoUrl ? (
                            <img src={user.photoUrl} alt="Profile" className="profile-img-small" />
                        ) : (
                            <span className="profile-default-icon">👤</span>
                        )}
                    </div>
                </button>
            </div>
        </nav>
    );
};

export default Navbar;
