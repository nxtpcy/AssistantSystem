<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2016-11-22T05:40:30Z</LastSaved>
  <Version>15.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>8016</WindowHeight>
  <WindowWidth>14808</WindowWidth>
  <WindowTopX>240</WindowTopX>
  <WindowTopY>108</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s17">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"
    ss:Bold="1"/>
  </Style>
  <Style ss:ID="s68">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s69">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center" ss:WrapText="1"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="20" ss:Color="#000000"
    ss:Bold="1"/>
   <NumberFormat ss:Format="@"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="6" ss:ExpandedRowCount="${size}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultRowHeight="14.4">
   <Column ss:AutoFitWidth="0" ss:Width="40.200000000000003"/>
   <Column ss:AutoFitWidth="0" ss:Width="87"/>
   <Column ss:AutoFitWidth="0" ss:Width="54"/>
   <Column ss:AutoFitWidth="0" ss:Width="152.4"/>
   <Column ss:AutoFitWidth="0" ss:Width="54"/>
   <Column ss:AutoFitWidth="0" ss:Width="73.8"/>
   <Row ss:AutoFitHeight="0" ss:Height="71.400000000000006">
    <Cell ss:MergeAcross="5" ss:StyleID="s69"><Data ss:Type="String">${title?default('')}</Data></Cell>
   </Row>
   <Row>
    <Cell ss:StyleID="s17"><Data ss:Type="String">序号</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">学号</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">姓名</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">银行卡号</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">补助金额</Data></Cell>
    <Cell ss:StyleID="s17"><Data ss:Type="String">备注</Data></Cell>
   </Row>
   <#list lists as zg>
   <Row>
    <Cell ss:StyleID="s68"><Data ss:Type="String">${zg.id?default('')}</Data></Cell>
    <Cell ss:StyleID="s68"><Data ss:Type="String">${zg.stuId?default('')}</Data></Cell>
    <Cell ss:StyleID="s68"><Data ss:Type="String">${zg.name?default('')}</Data></Cell>
    <Cell ss:StyleID="s68"><Data ss:Type="String">${zg.bankNo?default('')}</Data></Cell>
    <Cell ss:StyleID="s68"><Data ss:Type="String">${zg.money?default('')}</Data></Cell>
    <Cell ss:StyleID="s68"><Data ss:Type="String">${zg.beizhu?default('')}</Data></Cell>
   </Row>
   </#list>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3" x:Data="&amp;C&amp;P"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>7</ActiveRow>
     <ActiveCol>3</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
