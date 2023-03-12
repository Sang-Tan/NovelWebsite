CREATE TABLE Novel(
    ID INT PRIMARY KEY IDENTITY(1,1),
    [Owner] [int] NOT NULL,
    [Summary] [nvarchar](max) NOT NULL,
    [SummaryTemp] [nvarchar](max) NULL,
    [Name] [nvarchar](200) NOT NULL,
    [NameTemp] [nvarchar](200) NULL,
    [Image] [nvarchar](200) NOT NULL,
    [ImageTemp] [nvarchar](200) NULL,
    [IsPending] [bit] NOT NULL,
    [Status] [nvarchar](50) NOT NULL);