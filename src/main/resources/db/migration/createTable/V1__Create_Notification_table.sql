CREATE TABLE Notification(
    ID INT PRIMARY KEY IDENTITY(1,1),
    [User_ID] [int] NOT NULL,
    [Body] [nvarchar](max) NOT NULL,
    [Link] [nvarchar](200) NULL
);