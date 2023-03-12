CREATE TABLE Volume(
    ID INT PRIMARY KEY IDENTITY(1,1),
    [NovelID] [int] NOT NULL,
    [Name] [nvarchar](200) NOT NULL,
    [NameTemp] [nvarchar](200) NULL,
    [Image] [nvarchar](200) NOT NULL,
    [ImageTemp] [nvarchar](200) NULL,
    [VolumeOrder] [smallint] NOT NULL);