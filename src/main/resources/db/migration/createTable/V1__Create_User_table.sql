CREATE TABLE User(
    ID INT PRIMARY KEY IDENTITY(1,1),
    [Password] [char](32) NOT NULL,
    [UserName] [nvarchar](50) NOT NULL,
    [DisplayName] [nvarchar](50) NOT NULL,
    [IsActive] [bit] NOT NULL,
    [Avatar] [varchar](200) NULL,
    [Role] [nvarchar](100) NOT NULL
);