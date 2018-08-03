<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2016-11-30T11:17:04Z</LastSaved>
  <Version>15.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>9360</WindowHeight>
  <WindowWidth>23040</WindowWidth>
  <WindowTopX>0</WindowTopX>
  <WindowTopY>0</WindowTopY>
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
  <Style ss:ID="s63">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="18" ss:Color="#000000"
    ss:Bold="1"/>
  </Style>
  <Style ss:ID="s71">
   <Alignment ss:Horizontal="Left" ss:Vertical="Center"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="10.5" ss:Color="#000000"/>
  </Style>
  <Style ss:ID="s73">
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12" ss:Color="#000000"
    ss:Bold="1"/>
  </Style>
  <Style ss:ID="s86">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
  </Style>
  <Style ss:ID="s87">
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="6" ss:ExpandedRowCount="${size}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultRowHeight="14.55">
   <Column ss:AutoFitWidth="0" ss:Width="75"/>
   <Column ss:AutoFitWidth="0" ss:Width="104.39999999999999"/>
   <Column ss:AutoFitWidth="0" ss:Width="96.6"/>
   <Column ss:AutoFitWidth="0" ss:Width="37.200000000000003"/>
   <Column ss:AutoFitWidth="0" ss:Width="84.600000000000009"/>
   <Column ss:AutoFitWidth="0" ss:Width="70.2"/>
   <Row ss:AutoFitHeight="0" ss:Height="22.200000000000003">
    <Cell ss:MergeAcross="5" ss:StyleID="s63"><Data ss:Type="String">研究生助管${month?default('')}月考核表</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0">
    <Cell><Data ss:Type="String">单位：</Data></Cell>
    <Cell><Data ss:Type="String">${deptName?default('')}</Data></Cell>
    <Cell ss:Index="5"><Data ss:Type="String">考核月份：</Data></Cell>
    <Cell><Data ss:Type="String">${year?default('')}年${month?default('')}月</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s87"><Data ss:Type="String">姓名</Data></Cell>
    <Cell ss:StyleID="s87"><Data ss:Type="String">学号</Data></Cell>
    <Cell ss:StyleID="s87"><Data ss:Type="String">银行卡号</Data></Cell>
    <Cell ss:StyleID="s87"><Data ss:Type="String">金额</Data></Cell>
    <Cell ss:StyleID="s87"><Data ss:Type="String">考核老师签字</Data></Cell>
    <Cell ss:StyleID="s87"><Data ss:Type="String">备注</Data></Cell>
   </Row>
   <#list lists as zg>
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s87"><Data ss:Type="String">${zg.name?default('')}</Data></Cell>
    <Cell ss:StyleID="s87"><Data ss:Type="String">${zg.stuId?default('')}</Data></Cell>
    <Cell ss:StyleID="s87"><Data ss:Type="String">${zg.bankNo?default('')}</Data></Cell>
    <Cell ss:StyleID="s87"><Data ss:Type="String">${zg.money?default('')}</Data></Cell>
    <Cell ss:StyleID="s87"/>
    <Cell ss:StyleID="s87"><Data ss:Type="String">${zg.beizhu?default('')}</Data></Cell>
   </Row>
   </#list>
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s71"><ss:Data ss:Type="String"
      xmlns="http://www.w3.org/TR/REC-html40"><Font html:Color="#000000">注：</Font><Font
       html:Face="Times New Roman" x:Family="Roman" html:Color="#000000">1</Font><Font
       html:Color="#000000">、此表请每月</Font><Font html:Face="Times New Roman"
       x:Family="Roman" html:Color="#000000">25</Font><Font html:Color="#000000">日前报研究生管理处，以便核发补助，过时不予补发。</Font></ss:Data></Cell>
    <Cell ss:StyleID="s86"/>
    <Cell ss:StyleID="s86"/>
    <Cell ss:StyleID="s86"/>
    <Cell ss:StyleID="s86"/>
    <Cell ss:StyleID="s86"/>
   </Row>
   <Row ss:AutoFitHeight="0">
    <Cell><Data ss:Type="String">    2、补助可以根据指导老师考核情况调整，但不能超过规定的上限。</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0">
    <Cell ss:StyleID="s73"><Data ss:Type="String">单位审批人：</Data></Cell>
    <Cell ss:Index="4" ss:StyleID="s73"><Data ss:Type="String">审批时间：</Data></Cell>
   </Row>
   <Row ss:AutoFitHeight="0">
    <Cell ss:Index="4" ss:StyleID="s73"><Data ss:Type="String">单位盖章：</Data></Cell>
   </Row>
   <Row ss:Index="15" ss:AutoFitHeight="0" ss:Height="15"/>
   <Row ss:Index="17" ss:AutoFitHeight="0" ss:Height="25.2"/>
   <Row ss:AutoFitHeight="0" ss:Height="30.45"/>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3" x:Data="&amp;C&amp;P"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Unsynced/>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <PageBreakZoom>60</PageBreakZoom>
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>1</ActiveRow>
     <ActiveCol>1</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
