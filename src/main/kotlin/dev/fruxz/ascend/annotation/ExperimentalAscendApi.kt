package dev.fruxz.ascend.annotation

/**
 * Marks the annotated element as part of the experimental Ascend API.
 *
 * This annotation should be used to indicate that the annotated element is experimental
 * and may undergo changes or be removed in future versions. It serves as a warning for
 * developers to use the element with caution.
 * @author Fruxz
 * @since 2023.1
 **/
@MustBeDocumented
@RequiresOptIn("This is a experimental feature, proceed with caution!")
internal annotation class ExperimentalAscendApi
