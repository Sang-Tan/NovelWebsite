CREATE TABLE UserToken(
    ID INT PRIMARY KEY IDENTITY(1,1),
    [UserID] [int] NOT NULL,
    [ValidatorHash] [char](32) NOT NULL);