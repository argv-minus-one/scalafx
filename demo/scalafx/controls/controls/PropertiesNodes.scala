package scalafx.controls.controls

import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.text._
import scalafx.Includes._
import scalafx.beans.property._
import scalafx.scene.Node._
import scalafx.scene.control._
import scalafx.scene.layout.GridPane._
import scalafx.scene.layout.GridPane
import scalafx.scene.text.Font.font
import scalafx.scene.Node

/**
 * Basic class to control a control properties
 *
 *  @tparam N scalafx.scene.Node subclass
 *
 *  @param target Node to be manipulated
 *  @param title TitledPane titled
 */
abstract class PropertiesNodes[T](target: T, title: String) extends TitledPane {

  private var index = 0

  protected def resetProperties = {}

  protected val btnReset = new Button {
    text = "Reset"
    onAction = resetProperties
    alignment = Pos.CENTER
  }

  private val controlsPane = new GridPane {
    hgap = 5
    vgap = 5
    hgrow = Priority.NEVER
  }

  /**
   * Add a Control Node with its respective title
   *
   * @param title Control Node title
   * @param control Control Node
   */
  protected def addNode(title: String, control: Node) {
    controlsPane.add(new Label {
      font = PropertiesNodes.TitleFont
      labelFor = control
      text = title
      textAlignment = TextAlignment.RIGHT
    }.asInstanceOf[Node], 0, index)
    controlsPane.add(control, 1, index)
    index += 1
  }

  /**
   * Add a Control Node occupying 2 columns
   *
   * @param control Control Node
   */
  protected def addNode(control: Node) {
    controlsPane.add(control, 0, index, 2, 1)
    index += 1
  }

  /**
   * Add 2 Controls Nodes to occupy a row.
   *
   * @param control1 Control Node 1
   * @param control2 Control Node 2
   */
  protected def addNodes(control1: Node, control2: Node) {
    controlsPane.add(control1, 0, index)
    controlsPane.add(control2, 1, index)
    index += 1
  }

  protected def fillDoublePropertyFromText(property: DoubleProperty, field: TextField, cleanAfterAction: Boolean = true, onError: () => Unit = () => ()) {
    try {
      val txt = field.text.get
      property.value = txt.toDouble
    } catch {
      case t: NumberFormatException => onError
    }

    if (cleanAfterAction) {
      field.text = ""
    }

  }

  protected def fillIntPropertyFromText(property: IntegerProperty, field: TextField, cleanAfterAction: Boolean = true, onError: () => Unit = () => ()) {
    try {
      val txt = field.text.get
      property.value = txt.toInt
    } catch {
      case t: NumberFormatException => onError
    }

    if (cleanAfterAction) {
      field.text = ""
    }

  }

  delegate.text = title
  content = controlsPane

}

object PropertiesNodes {

  private val lblBase = new Label
  private val fontBase = lblBase.font.get()

  val TitleFont = font(fontBase.getFamily, FontWeight.BOLD, fontBase.getSize)
}