package uk.gov.hmrc.ndrrprotofrontend.models

import uk.gov.hmrc.govukfrontend.views.Aliases.{HeadCell, Table, TableRow, Text}

final case class TableBuilder(
                               tableRows: Seq[Seq[TableRow]],
                               headCells: Option[Seq[HeadCell]],
                               caption: Option[String] = None,
                               captionClass: String = "",
                               firstCellHeader: Boolean = false
                             )

object TableBuilder {

  def buildTable(tableBuilder: TableBuilder): Table = {
    Table(
      rows = tableBuilder.tableRows,
      head = tableBuilder.headCells,
      caption = tableBuilder.caption,
      captionClasses = tableBuilder.captionClass,
      firstCellIsHeader = tableBuilder.firstCellHeader
    )
  }

  def createRows(data: Seq[Seq[String]]): Seq[Seq[TableRow]] =
    data.map(_.map(cell => TableRow(content = Text(cell))))

  def createHeadCells(headers: Seq[String]): Option[Seq[HeadCell]] =
    Some(headers.map(header => HeadCell(content = Text(header))))
}
