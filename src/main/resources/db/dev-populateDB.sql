INSERT INTO application_information  (uuid, clientId, thirdPartyApplicationName, thirdPartyScopeSelectionScreenURI, thirdPartyNotifyUri, redirectURI, clientSecret) VALUES ('550e8400-e29b-41d4-a716-4466554413a0', 'third_party', 'Third Party (localhost - dev)', 'http://localhost:8080/ThirdParty/RetailCustomer/ScopeSelection', 'http://localhost:8080/ThirdParty/espi/1_1/Notification', 'http://localhost:8080/ThirdParty/espi/1_1/OAuthCallBack', 'secret');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (1, 'FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (1, 'FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');