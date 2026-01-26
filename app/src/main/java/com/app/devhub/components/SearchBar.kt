package com.app.devhub.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun searchBar(value: String,
              onValueChange: (String) -> Unit,
              onSearch: () -> Unit,
              modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier= modifier,
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        singleLine = true,
        placeholder = { Text("Buscar usuário do GitHub") },
        shape = RoundedCornerShape(50),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),

        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        )
    )
}

@Preview
@Composable
fun searchBarPreview() {
    searchBar(value = "", onValueChange = {}, onSearch = {})
}
