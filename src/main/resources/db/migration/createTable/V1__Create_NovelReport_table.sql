CREATE TABLE NovelReport(
    ID INT PRIMARY KEY IDENTITY(1,1),
    [NovelID] [int] NOT NULL,
    [ReporterID] [int] NOT NULL,
    [Reason] [nvarchar](max) NOT NULL,
    [CheckTime] [datetime] NOT NULL);