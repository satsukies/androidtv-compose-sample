package dev.satsukies.tvcomposesample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.databinding.DataBindingUtil
import com.google.android.material.composethemeadapter.MdcTheme
import dev.satsukies.tvcomposesample.databinding.ActivitySampleBinding

class SampleActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = DataBindingUtil.setContentView<ActivitySampleBinding>(
      this,
      R.layout.activity_sample
    )

    binding.compose.setContent {
      MdcTheme {
        ErrorCompose()
      }
    }
  }

  @Preview(device = "id:tv_1080p")
  @Composable
  private fun ErrorCompose() {
    // 特定の要素に対してfocusをrequestするために使う
    val focusRequester = remember { FocusRequester() }

    // button要素に対して発生したinteractionを起点にBGとテキストの色を変更するために使う
    val detailButtonSource = remember { MutableInteractionSource() }
    val closeButtonSource = remember { MutableInteractionSource() }
    val isDetailFocused by detailButtonSource.collectIsFocusedAsState()
    val isCloseFocused by closeButtonSource.collectIsFocusedAsState()

    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.black)),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = stringResource(id = R.string.error_404),
        modifier = Modifier.width(314.dp),
      )
      Spacer(modifier = Modifier.height(32.dp))
      Text(
        text = stringResource(id = R.string.error_404_title),
        fontSize = 34.sp,
        color = colorResource(id = R.color.text_white),
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(id = R.string.error_404_description),
        fontSize = 18.sp,
        color = colorResource(id = R.color.text_white),
      )
      Spacer(modifier = Modifier.height(40.dp))
      Row {
        Button(
          modifier = Modifier
            .width(200.dp)
            .height(42.dp)
            .focusRequester(focusRequester),
          interactionSource = detailButtonSource,
          colors = ButtonDefaults.textButtonColors(
            backgroundColor = bgColor(isFocused = isDetailFocused),
            contentColor = textColor(isFocused = isDetailFocused),
          ),
          onClick = { onClickDetail() }
        ) {
          Text(
            text = stringResource(id = R.string.action_show_detail),
            fontSize = 18.sp,
          )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
          modifier = Modifier
            .width(200.dp)
            .height(42.dp),
          interactionSource = closeButtonSource,
          colors = ButtonDefaults.textButtonColors(
            backgroundColor = bgColor(isFocused = isCloseFocused),
            contentColor = textColor(isFocused = isCloseFocused),
          ),
          onClick = { onClickClose() }
        ) {
          Text(
            text = stringResource(id = R.string.action_close),
            fontSize = 18.sp,
          )
        }
      }
    }

    LaunchedEffect(Unit) {
      focusRequester.requestFocus()
    }
  }

  @Composable
  private fun bgColor(isFocused: Boolean) = if (isFocused) {
    colorResource(id = R.color.light_gray)
  } else {
    colorResource(id = R.color.dark_gray)
  }

  @Composable
  private fun textColor(isFocused: Boolean) = if (isFocused) {
    colorResource(id = R.color.text_black)
  } else {
    colorResource(id = R.color.text_white)
  }

  private fun onClickDetail() {
    Toast.makeText(this, R.string.toast_on_click_detail, Toast.LENGTH_SHORT).show()
  }

  private fun onClickClose() {
    finish()
  }
}