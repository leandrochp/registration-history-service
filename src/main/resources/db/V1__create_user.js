db = db.getSiblingDB('registration_history_service');
db.createUser({
    user: 'registry',
    pwd: 'passw0rd',
    roles: [{
        role: 'readWrite',
        db: 'registration_history_service'
    }]
});
