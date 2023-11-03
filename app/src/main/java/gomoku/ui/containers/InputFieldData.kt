package gomoku.ui.containers

//TODO(Be careful with the specified parameter validationParameter function)
data class InputFieldData(val text: String, val iconId: Int, val onClick: () -> Unit = {} ,val validationParameter: (String)->Boolean ={true},)
