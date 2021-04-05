package org.vaadin.example.view

import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.binder.Binder.BindingBuilder
import com.vaadin.flow.data.binder.Validator
import com.vaadin.flow.data.validator.StringLengthValidator
import com.vaadin.flow.function.SerializablePredicate
import java.util.ArrayList


class BorderedField(label: String, val requred: Boolean=false): TextField(label) {
    private val content = Content()
    private val binder: Binder<Content> = Binder()
    private val validators: MutableList<Validator<String?>> = ArrayList<Validator<String?>>()

    init {
        addThemeName("bordered")
        isRequired=requred
        if(requred) {
            addValidator(
                StringLengthValidator ("Field required", 2, 100)
            )
        }
    }

    internal class Content {
        var content: String? = null
    }

    fun ValidTextField() {
        binder.bean = content
    }

    fun addValidator(
        predicate: SerializablePredicate<String?>?,
        errorMessage: String?
    ) {
        addValidator(Validator.from(predicate, errorMessage))
    }

    fun addValidator(validator: Validator<String?>) {
        validators.add(validator)
        build()
    }

    private fun build() {
        val builder: BindingBuilder<Content, String?> = binder.forField(this)
        for (v in validators) {
            builder.withValidator(v)
        }
        builder.bind(
            { obj: Content -> obj.content }
        ) { obj: Content, content: String? ->
            obj.content = content
        }
    }
}